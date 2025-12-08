plugins {
    kotlin("jvm") version "2.2.0"
}

group = "dev.rudii14"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks {
    wrapper {
        gradleVersion = "8.8"
    }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.0")
    implementation("io.ktor:ktor-client-core:3.3.2")
    implementation("io.ktor:ktor-client-cio:3.3.2")
}
