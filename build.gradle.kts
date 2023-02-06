plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.7.0"
}

version = "0.1"
group = "com.softamo.githubratelimiting"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("info.picocli:picocli-codegen")
    implementation("info.picocli:picocli")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.picocli:micronaut-picocli")
    runtimeOnly("ch.qos.logback:logback-classic")
}

application {
    mainClass.set("com.softamo.githubratelimiting.GithubRateLimitingCommand")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.softamo.githubratelimiting.*")
    }
}



