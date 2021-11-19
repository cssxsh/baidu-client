package xyz.cssxsh.baidu

import kotlinx.serialization.json.*
import xyz.cssxsh.baidu.oauth.*

/**
 * * AIP 文档 [https://ai.baidu.com/ai-doc]
 * * Token 认证 [https://ai.baidu.com/ai-doc/REFERENCE/Ck3dwjhhu]
 */
interface AipClient : BaiduAuthClient {

    companion object {
        const val Timeout: Long = 30 * 1000L

        val Json = Json {
            ignoreUnknownKeys = true
        }
    }

    /**
     * 获取 Token
     */
    suspend fun token(): AuthorizeAccessToken = getClientCredentialsToken().also { saveToken(token = it) }

    /**
     * 刷新 Token
     */
    suspend fun refresh(): AuthorizeAccessToken = getRefreshToken().also { saveToken(token = it) }
}