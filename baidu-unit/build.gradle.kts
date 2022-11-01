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
    api(project(":baidu-aip"))
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