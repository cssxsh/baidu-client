plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("me.him188.maven-central-publish")
}

mavenCentralPublish {
    useCentralS01()
    singleDevGithubProject("cssxsh", "baidu-client")
    licenseFromGitHubProject("AGPL-3.0")
    workingDir = System.getenv("PUBLICATION_TEMP")?.let { file(it).resolve(projectName) }
        ?: project.buildDir.resolve("publishing-tmp")
}

dependencies {
    api("io.ktor:ktor-client-okhttp:2.1.0")
    api("io.ktor:ktor-client-encoding:2.1.0")
    api("io.ktor:ktor-client-content-negotiation:2.1.0")
    api("io.ktor:ktor-serialization-kotlinx-json:2.1.0")
    api("com.squareup.okhttp3:okhttp:4.10.0")
    testImplementation(kotlin("test"))
}

kotlin {
    explicitApi()
    target.compilations {
        all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks {
    test {
        useJUnitPlatform()
    }
}