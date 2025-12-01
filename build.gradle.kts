plugins {
    kotlin("jvm") version "2.1.10"
}
val kotlinCore_version: String by project
val kotlinServer_version: String by project
val logback_version: String by project
val mavenUser: String by project
val mavenToken: String by project

group = "com.etherfirma.aoc2025"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        credentials {
            username = mavenUser
            password = mavenToken
        }
        url = uri ("https://maven.etherfirma.com/snapshots")
    }
}

dependencies {
    testImplementation(kotlin("test"))
    // etherfirma libraries
    implementation ("com.etherfirma:kotlin-core:$kotlinCore_version")
    implementation ("com.etherfirma:kotlin-server:$kotlinServer_version")
    // logging
    implementation("ch.qos.logback:logback-classic:$logback_version")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}