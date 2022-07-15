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
    api("io.ktor:ktor-client-okhttp:2.0.3")
    api("io.ktor:ktor-client-encoding:2.0.3")
    api("io.ktor:ktor-client-content-negotiation:2.0.3")
    api("io.ktor:ktor-serialization-kotlinx-json:2.0.3")
    api("com.squareup.okhttp3:okhttp:4.10.0")
    testImplementation(kotlin("test"))
}

kotlin {
    explicitApi()
    target.compilations {
        all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    test {
        useJUnitPlatform()
    }
}