import xyz.cssxsh.maven.*

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `maven-publish`
}

setGithub()

dependencies {
    implementation(ktor("client", Versions.ktor))
    implementation(ktor("client-serialization", Versions.ktor))
    implementation(ktor("client-okhttp", Versions.ktor))
    testImplementation(kotlin("test-junit5"))
    testImplementation(junit("jupiter", Versions.junit))
    testImplementation("org.seleniumhq.selenium:selenium-java:4.0.0-beta-4")
}

kotlin {
    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
            languageSettings.useExperimentalAnnotation("kotlinx.serialization.ExperimentalSerializationApi")
            languageSettings.useExperimentalAnnotation("kotlinx.serialization.InternalSerializationApi")
        }
    }
    target {
        compilations.configureEach {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}