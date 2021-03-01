import java.io.File
import java.util.*

object BintrayProperties {
    var properties = File("local.properties")
    private val local get() = Properties().apply {
        properties.inputStream().use {
            load(it)
        }
    }

    val user: String get() = local.getProperty("BINTRAY_USER")
    val key: String get() = local.getProperty("BINTRAY_KEY")
    val org: String get() = local.getProperty("BINTRAY_ORG")
    val license: String get() = local.getProperty("BINTRAY_LICENSE")
    val licenseName: String get() = local.getProperty("BINTRAY_LICENSE_NAME")
    val licenseUrl: String = local.getProperty("BINTRAY_LICENSE_URL")
    val repo: String get() = local.getProperty("BINTRAY_REPO")
    val task: String get() = local.getProperty("PUBLICATION")

    val githubUrl: String get() = local.getProperty("GITHUB_URL")
    val githubRepo: String get() = local.getProperty("GITHUB_REPO")
    val githubPage: String get() = local.getProperty("GITHUB_PAGE")
    val githubId: String get() = local.getProperty("GITHUB_ID")
    val githubEmail: String get() = local.getProperty("GITHUB_EMAIL")
}