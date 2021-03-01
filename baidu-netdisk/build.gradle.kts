import java.util.Date

plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("plugin.serialization") version Versions.kotlin
    `maven-publish`
    id("com.jfrog.bintray") version Versions.bintray
}

bintray {
    user = BintrayProperties.user
    key = BintrayProperties.key
    publish = true
    setPublications(BintrayProperties.task)

    pkg.apply {
        userOrg = BintrayProperties.org
        repo = BintrayProperties.repo
        name = project.name
        vcsUrl = BintrayProperties.githubUrl
        githubRepo = BintrayProperties.githubRepo
        setLicenses(BintrayProperties.license)
        version.apply {
            name = project.version.toString()
            released = Date().toString()
        }
    }
}

publishing {
    publications {
        create<MavenPublication>(BintrayProperties.task) {
            from(components["java"])
            artifact(tasks.kotlinSourcesJar)

            pom {
                packaging = "jar"
                name.set(BintrayProperties.repo)
                url.set(BintrayProperties.githubUrl)
                licenses {
                    license {
                        name.set(BintrayProperties.licenseName)
                        url.set(BintrayProperties.licenseUrl)
                    }
                }
                developers {
                    developer {
                        id.set(BintrayProperties.githubId)
                        email.set(BintrayProperties.githubEmail)
                        url.set(BintrayProperties.githubPage)
                    }
                }
                scm {
                    connection.set(BintrayProperties.githubUrl)
                    developerConnection.set(BintrayProperties.githubUrl)
                    url.set(BintrayProperties.githubUrl)
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