package com.softamo.githubratelimiting;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.client.annotation.Client;

@Client(id = "github")
@Header(name = HttpHeaders.USER_AGENT, value = "RateLimitingCli")
public interface GithubClient {

    @Get("/users/{username}")
    HttpResponse<?> usersHeaders(@PathVariable String username);

    @Get("/users/{username}")
    HttpResponse<?> usersHeaders(@PathVariable String username, @Header String authorization);
}
