import java.util.Date

plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("plugin.serialization") version Versions.kotlin
    `maven-publish`
    id("com.jfrog.bintray") version Versions.bintray
}

bintray {
    user = BintraySetting.user
    key = BintraySetting.key
    publish = true
    setPublications(BintraySetting.task)

    pkg.apply {
        userOrg = BintraySetting.org
        repo = BintraySetting.repo
        name = project.name
        vcsUrl = BintraySetting.githubUrl
        githubRepo = BintraySetting.githubRepo
        setLicenses(BintraySetting.license)
        version.apply {
            name = project.version.toString()
            released = Date().toString()
        }
    }
}

publishing {
    publications {
        create<MavenPublication>(BintraySetting.task) {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            artifact(tasks.kotlinSourcesJar)
            artifact(tasks.jar)

            pom {
                packaging = "jar"
                name.set(BintraySetting.repo)
                url.set(BintraySetting.githubUrl)
                licenses {
                    license {
                        name.set(BintraySetting.licenseName)
                        url.set(BintraySetting.licenseUrl)
                    }
                }
                developers {
                    developer {
                        id.set(BintraySetting.githubId)
                        email.set(BintraySetting.githubEmail)
                        url.set(BintraySetting.githubPage)
                    }
                }
                scm {
                    connection.set(BintraySetting.githubUrl)
                    developerConnection.set(BintraySetting.githubUrl)
                    url.set(BintraySetting.githubUrl)
                }
            }
        }
    }
}

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
            languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
        }
    }
}

tasks {

    test {
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions.freeCompilerArgs += "-Xjvm-default=all"
        kotlinOptions.jvmTarget = "11"
    }

    compileTestKotlin {
        kotlinOptions.freeCompilerArgs += "-Xjvm-default=all"
        kotlinOptions.jvmTarget = "11"
    }

}