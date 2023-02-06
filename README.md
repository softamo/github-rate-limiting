CLI application, built with the [Micronaut Framework](https://micronaut.io), to get Rate Limits for a particular user.

- [Github Rates Limit](https://docs.github.com/en/rest/overview/resources-in-the-rest-api?apiVersion=2022-11-28#rate-limits)
- [Github About Rate Limits](https://docs.github.com/en/rest/rate-limit?apiVersion=2022-11-28#about-rate-limits)


## Usage

### Build the CLI
```
./gradlew build
```


### Run the CLI

```
java -jar build/libs/github-rate-limiting-0.1-all.jar -u sdelamo    
12:14:33.132 [main] INFO  i.m.context.env.DefaultEnvironment - Established active environments: [cli]
Limit: 60
Remaining: 57
Reset: 1675685104
Rate-limiting resets in 00:50:30
Resource: core
Used: 3
```

Or with a token `java -jar build/libs/github-rate-limiting-0.1-all.jar -u sdelamo -t`
