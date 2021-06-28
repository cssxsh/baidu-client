package xyz.cssxsh.maven

import org.gradle.api.Action
import java.util.*
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.*

fun Project.properties(path: String) = Properties().also {
    rootProject.rootDir.resolve(path).inputStream().use { stream ->
        it.load(stream)
    }
}

data class Config(
    var path: String = "local.properties"
)

data class GithubProperties(
    val url: String,
    val repo: String,
    val page: String,
    val id: String,
    val email: String,
    val token: String
)

fun Project.GithubProperties(path: String): GithubProperties {
    val properties = properties(path)
    return GithubProperties(
        url = properties.getProperty("GITHUB_URL"),
        repo = properties.getProperty("GITHUB_REPO"),
        page = properties.getProperty("GITHUB_PAGE"),
        id = properties.getProperty("GITHUB_ID"),
        email = properties.getProperty("GITHUB_EMAIL"),
        token = properties.getProperty("GITHUB_TOKEN")
    )
}

fun Project.publishing(configure: Action<PublishingExtension>): Unit = extensions.configure("publishing", configure)

fun Project.setGithub(config: Config = Config()) {

    val github = GithubProperties(config.path)

    println("$name with ${github.id}")

    publishing {
        repositories {
            maven("https://maven.pkg.github.com/${github.repo}") {
                credentials {
                    username = github.id
                    password = github.token
                }
            }
        }
        publications {
            create<MavenPublication>("github") {
                from(components["java"])
                artifact(tasks.named<Jar>("kotlinSourcesJar"))

                pom {
                    packaging = "jar"
                    name.set(github.repo)
                    url.set(github.url)
                    licenses {
                        license {
                            name.set("GNU AFFERO GENERAL PUBLIC LICENSE, Version 3")
                            url.set("https://www.gnu.org/licenses/agpl-3.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set(github.id)
                            email.set(github.email)
                            url.set(github.page)
                        }
                    }
                    scm {
                        connection.set(github.url)
                        developerConnection.set(github.url)
                        url.set(github.url)
                    }
                }
            }
        }
    }
}

