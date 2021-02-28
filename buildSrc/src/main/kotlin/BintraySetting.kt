
object BintraySetting {
    val user: String = System.getenv("BINTRAY_USER")
    val key: String = System.getenv("BINTRAY_KEY")
    val org: String = System.getenv("BINTRAY_ORG")
    const val license: String = "AGPL-3.0"
    const val repo: String = "baidu-client"
    const val task: String = "publishToBintray"

    const val githubUrl: String = "https://github.com/cssxsh/baidu-client.git"
    const val githubRepo: String = "cssxsh/baidu-client"
    const val githubPage: String = "https://github.com/cssxsh"
    const val githubId: String = "cssxsh"
    const val githubEmail: String = "cssxsh@gamil.com"

    const val licenseName: String = "GNU AFFERO GENERAL PUBLIC LICENSE, Version 3"
    const val licenseUrl: String = "https://www.gnu.org/licenses/agpl-3.0.txt"
}