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
        ?: buildDir.resolve("publishing-tmp")
}

dependencies {
    testImplementation(kotlin("test"))
    //
    implementation(platform("io.ktor:ktor-bom:2.1.3"))
    api("io.ktor:ktor-client-okhttp")
    api("io.ktor:ktor-client-encoding")
    api("io.ktor:ktor-client-content-negotiation")
    api("io.ktor:ktor-serialization-kotlinx-json")
    //
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    api("com.squareup.okhttp3:okhttp")
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