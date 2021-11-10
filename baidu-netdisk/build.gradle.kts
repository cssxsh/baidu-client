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
    implementation(project(":baidu-oauth"))
    implementation(ktor("client", Versions.ktor))
    implementation(ktor("client-serialization", Versions.ktor))
    implementation(ktor("client-encoding", Versions.ktor))
    implementation(ktor("client-okhttp", Versions.ktor))
    testImplementation(kotlin("test"))
}

kotlin {
    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}