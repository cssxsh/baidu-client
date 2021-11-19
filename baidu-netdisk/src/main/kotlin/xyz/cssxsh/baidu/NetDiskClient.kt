package xyz.cssxsh.baidu

import kotlinx.serialization.json.*

interface NetDiskClient : BaiduUserAuthClient {
    val appDataFolder: String

    companion object {
        const val Timeout: Long = 30 * 1000L

        val Json = Json {
            ignoreUnknownKeys = true
        }
    }
}