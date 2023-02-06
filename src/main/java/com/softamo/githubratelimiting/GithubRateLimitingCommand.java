package com.softamo.githubratelimiting;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Command(name = "github-rate-limiting", description = "It outputs the current Github Rate Limit for a user.",
        mixinStandardHelpOptions = true)
public class GithubRateLimitingCommand implements Runnable {
    private static final String X_RATE_LIMIT = "X-RateLimit-";
    private static final String X_RATE_LIMIT_RESET = "X-RateLimit-Reset";

    @Option(names = {"-u", "--username"}, description = "Github username", required = true)
    String username;

    @Option(names = {"-t", "--token"}, description = "Github Token", interactive = true)
    String token;

    @Inject
    GithubClient githubClient;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(GithubRateLimitingCommand.class, args);
    }

    public void run() {
        try {
            HttpResponse<?> response = token != null ?
                githubClient.usersHeaders(username, BasicAuthUtils.basicAuth(username, token)) :
                githubClient.usersHeaders(username);
            logRateLimitingHeaders(response.getHeaders());
        } catch(HttpClientResponseException e) {
            out("WARNING: User " + username + " Rate Limited");
            logRateLimitingHeaders(e.getResponse().getHeaders());
        }
    }

    private static void logRateLimitingHeaders(HttpHeaders headers) {
        for (String headerName : headers.names()) {
            if (headerName.startsWith(X_RATE_LIMIT)) {
                String xRateLimitHeaderName = headerName.replace(X_RATE_LIMIT, "");
                out(xRateLimitHeaderName + ": " + headers.get(headerName));
                if (headerName.equals(X_RATE_LIMIT_RESET)) {
                    String value = headers.get(headerName);
                    if (value != null) {
                        long resetEpoch = Long.parseLong(value);
                        OffsetDateTime currentTimeInUtc = OffsetDateTime.now(ZoneOffset.UTC);
                        long secondsSinceEpoch = currentTimeInUtc.toInstant().toEpochMilli() / 1000;
                        out("Rate-limiting resets in " + timeString(resetEpoch - secondsSinceEpoch));
                    }
                }
            }
        }
    }

    private static String timeString(Long totalSecs) {
        Long hours = totalSecs / 3600;
        Long minutes = (totalSecs % 3600) / 60;
        Long seconds = totalSecs % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private static void out(String message) {
        System.out.println(message);
    }
}
