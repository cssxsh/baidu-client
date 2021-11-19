package xyz.cssxsh.baidu.oauth

import io.ktor.client.request.*
import xyz.cssxsh.baidu.*

internal const val TOKEN = "https://aip.baidubce.com/oauth/2.0/token"

/**
 * [wiki](https://ai.baidu.com/ai-doc/REFERENCE/Ck3dwjhhu)
 */
internal suspend fun AipClient.getClientCredentialsToken(): AuthorizeAccessToken = useHttpClient { client ->
    client.post(TOKEN) {
        parameter("grant_type", GrantType.CLIENT_CREDENTIALS)
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
    }
}

/**
 * [wiki](https://ai.baidu.com/ai-doc/REFERENCE/Ck3dwjhhu)
 */
internal suspend fun AipClient.getRefreshToken(): AuthorizeAccessToken = useHttpClient { client ->
    client.post(TOKEN) {
        parameter("grant_type", GrantType.REFRESH)
        parameter("refresh_token", refreshToken)
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
    }
}