plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.spring") version "2.2.0"
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "io.github.dmitrysulman"
version = "0.0.1-SNAPSHOT"

ext["kotlin.version"] = "2.2.0"
ext["kotlin-coroutines.version"] = "1.10.2"
ext["kotlin-serialization.version"] = "1.9.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs:3.4.0")
    implementation("software.amazon.awssdk:sqs:2.31.73")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xannotation-default-target=param-property",
            "-Xwarning-level=IDENTITY_SENSITIVE_OPERATIONS_WITH_VALUE_TYPE:disabled"
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
