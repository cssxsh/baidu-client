package xyz.cssxsh.baidu.aip

import io.ktor.client.call.*
import io.ktor.client.request.*
import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.oauth.data.*

internal const val TOKEN = "https://aip.baidubce.com/oauth/2.0/token"

/**
 * [wiki](https://ai.baidu.com/ai-doc/REFERENCE/Ck3dwjhhu)
 */
internal suspend fun BaiduAuthClient<*>.getClientCredentialsToken(): AuthorizeAccessToken = useHttpClient { client ->
    client.post(TOKEN) {
        parameter("grant_type", GrantType.CLIENT_CREDENTIALS)
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
    }.body()
}

/**
 * [wiki](https://ai.baidu.com/ai-doc/REFERENCE/Ck3dwjhhu)
 */
internal suspend fun BaiduAuthClient<*>.getRefreshToken(): AuthorizeAccessToken = useHttpClient { client ->
    client.post(TOKEN) {
        parameter("grant_type", GrantType.REFRESH)
        parameter("refresh_token", refreshToken())
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
    }.body()
}