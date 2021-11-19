package xyz.cssxsh.baidu

interface NetDiskClient : BaiduUserAuthClient {
    val appDataFolder: String

    companion object {
        const val Timeout: Long = 30 * 1000L
    }
}