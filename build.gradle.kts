plugins {
    kotlin("jvm") version "2.0.0"
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
}
