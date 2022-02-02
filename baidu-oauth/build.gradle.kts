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
    implementation("io.ktor:ktor-client:1.6.5")
    implementation("io.ktor:ktor-client-serialization:1.6.5")
    implementation("io.ktor:ktor-client-encoding:1.6.5")
    implementation("io.ktor:ktor-client-okhttp:1.6.5")
    testImplementation(kotlin("test"))
}

kotlin {
    explicitApi()
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