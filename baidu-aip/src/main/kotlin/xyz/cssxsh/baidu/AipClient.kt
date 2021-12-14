package xyz.cssxsh.baidu

import xyz.cssxsh.baidu.oauth.*
import java.time.*

/**
 * * [AIP 文档](https://ai.baidu.com/ai-doc)
 * * [Token 认证](https://ai.baidu.com/ai-doc/REFERENCE/Ck3dwjhhu)
 */
interface AipClient : BaiduAuthClient {

    companion object {
        const val Timeout: Long = 30 * 1000L
    }

    /**
     * 获取 Token
     */
    override suspend fun token(): AuthorizeAccessToken {
        val time = OffsetDateTime.now()
        val token = getClientCredentialsToken()
        saveToken(token = token, time = time)
        return token
    }

    /**
     * 刷新 Token
     */
    override suspend fun refresh(): AuthorizeAccessToken {
        val time = OffsetDateTime.now()
        val token = getRefreshToken()
        saveToken(token = token, time = time)
        return token
    }
}