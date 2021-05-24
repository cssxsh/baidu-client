package xyz.cssxsh.bintray

import com.jfrog.bintray.gradle.BintrayExtension
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

data class BintrayProperties(
    val user: String,
    val key: String,
    val org: String,
    val license: String,
    val licenseName: String,
    val licenseUrl: String,
    val repo: String,
    val publication: String
)

fun Project.BintrayProperties(path: String) = BintrayProperties(
    user = properties(path).getProperty("BINTRAY_USER"),
    key = properties(path).getProperty("BINTRAY_KEY"),
    org = properties(path).getProperty("BINTRAY_ORG"),
    license = properties(path).getProperty("BINTRAY_LICENSE"),
    licenseName = properties(path).getProperty("BINTRAY_LICENSE_NAME"),
    licenseUrl = properties(path).getProperty("BINTRAY_LICENSE_URL"),
    repo = properties(path).getProperty("BINTRAY_REPO"),
    publication = properties(path).getProperty("PUBLICATION")
)

data class GithubProperties(
    val url: String,
    val repo: String,
    val page: String,
    val id: String,
    val email: String
)

fun Project.GithubProperties(path: String) = GithubProperties(
    url = properties(path).getProperty("GITHUB_URL"),
    repo = properties(path).getProperty("GITHUB_REPO"),
    page = properties(path).getProperty("GITHUB_PAGE"),
    id = properties(path).getProperty("GITHUB_ID"),
    email = properties(path).getProperty("GITHUB_EMAIL")
)

fun Project.bintray(configure: Action<BintrayExtension>): Unit = extensions.configure("bintray", configure)

fun Project.publishing(configure: Action<PublishingExtension>): Unit = extensions.configure("publishing", configure)

fun Project.setBintray(config: Config) {

    val setting = BintrayProperties(config.path)

    println(setting)

    val github = GithubProperties(config.path)


    bintray {
        user = setting.user
        key = setting.key
        publish = true
        setPublications(setting.publication)

        pkg.apply {
            userOrg = setting.org
            repo = setting.repo
            name = project.name
            vcsUrl = github.url
            githubRepo = github.repo
            setLicenses(setting.license)
            version.apply {
                name = project.version.toString()
                released = Date().toString()
            }
        }
    }

    publishing {
        publications {
            create<MavenPublication>(setting.publication) {
                from(components["java"])
                artifact(tasks.named<Jar>("kotlinSourcesJar"))

                pom {
                    packaging = "jar"
                    name.set(setting.repo)
                    url.set(github.url)
                    licenses {
                        license {
                            name.set(setting.licenseName)
                            url.set(setting.licenseUrl)
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

