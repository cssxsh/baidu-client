package xyz.cssxsh.baidu

import kotlinx.serialization.json.*

interface AipClient : BaiduAuthClient {

    companion object {
        const val Timeout: Long = 30 * 1000L

        val Json = Json {
            ignoreUnknownKeys = true
        }
    }
}