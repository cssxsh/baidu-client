plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("net.mamoe.maven-central-publish")
}

mavenCentralPublish {
    useCentralS01()
    singleDevGithubProject("cssxsh", "baidu-client", "cssxsh")
    licenseFromGitHubProject("AGPL-3.0", "master")
}

dependencies {
    implementation(ktor("client", Versions.ktor))
    implementation(ktor("client-serialization", Versions.ktor))
    implementation(ktor("client-okhttp", Versions.ktor))
    testImplementation(kotlin("test"))
}

kotlin {
    sourceSets {
        all {
//            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
//            languageSettings.useExperimentalAnnotation("kotlinx.serialization.ExperimentalSerializationApi")
//            languageSettings.useExperimentalAnnotation("kotlinx.serialization.InternalSerializationApi")
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