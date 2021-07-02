import xyz.cssxsh.maven.*

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `maven-publish`
}

setGithub()

dependencies {
    implementation(project(":baidu-oauth"))
    implementation(ktor("client", Versions.ktor))
    implementation(ktor("client-serialization", Versions.ktor))
    implementation(ktor("client-encoding", Versions.ktor))
    implementation(ktor("client-okhttp", Versions.ktor))
    testImplementation(kotlin("test-junit5"))
    testImplementation(junit("jupiter", Versions.junit))
}

kotlin {
    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlinx.serialization.ExperimentalSerializationApi")
            languageSettings.useExperimentalAnnotation("kotlinx.serialization.InternalSerializationApi")
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
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