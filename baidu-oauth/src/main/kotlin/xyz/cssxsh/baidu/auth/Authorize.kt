package xyz.cssxsh.baidu.auth

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.baidu.*

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 */
fun BaiduAuthClient.getWebAuthorizeUrl(
    type: AuthorizeType = AuthorizeType.IMPLICIT,
    state: String? = null,
    display: DisplayType = DisplayType.PAGE,
    force: Boolean? = null,
    confirm: Boolean? = null,
    login: LoginType? = null,
    extend: Map<String, Any?>? = null
): Url = URLBuilder(AuthorizeApi.AUTHORIZE).apply {
    parameters.apply {
        appendParameter("client_id", clientId)
        appendParameter("response_type", type)
        appendParameter("redirect_uri", redirect)
        appendParameter("scope", scope.joinToString(","))
        appendParameter("display", display)
        appendParameter("state", state)
        appendParameter("force_login", force?.toInt())
        appendParameter("confirm_login", confirm?.toInt())
        appendParameter("login_type", login)
        extend?.forEach { (name, value) ->
            appendParameter(name, value)
        }
    }
}.build()

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 */
suspend fun BaiduAuthClient.getAuthorizeToken(code: String): AuthorizeAccessToken = useHttpClient { client ->
    client.post(AuthorizeApi.TOKEN) {
        parameter("grant_type", GrantType.AUTHORIZATION)
        parameter("code", code)
        parameter("client_id", clientId)
        parameter("client_secret", clientSecret)
        parameter("redirect_uri", redirect)
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/client)
 */
suspend fun BaiduAuthClient.getCredentialsToken(): AuthorizeAccessToken = useHttpClient { client ->
    client.post(AuthorizeApi.TOKEN) {
        parameter("grant_type", GrantType.CREDENTIALS)
        parameter("client_id", clientId)
        parameter("client_secret", clientSecret)
        parameter("scope", scope.joinToString(","))
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
suspend fun BaiduAuthClient.getDeviceCode(): AuthorizeDeviceCode = useHttpClient { client ->
    client.post(AuthorizeApi.DEVICE_CODE) {
        parameter("client_id", clientId)
        parameter("response_type", "device_code")
        parameter("scope", scope.joinToString(","))
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
fun BaiduAuthClient.getDeviceAuthorizeUrl(
    code: String,
    display: DisplayType = DisplayType.PAGE,
    force: Boolean? = null,
    extend: Map<String, Any?>? = null
): Url = URLBuilder(AuthorizeApi.DEVICE).apply {
    parameters.apply {
        appendParameter("code", code)
        appendParameter("display", display)
        appendParameter("force_login", force?.toInt())
        extend?.forEach { (name, value) ->
            appendParameter(name, value)
        }
    }
}.build()

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
suspend fun BaiduAuthClient.getDeviceToken(code: String): AuthorizeAccessToken = useHttpClient { client ->
    client.post(AuthorizeApi.TOKEN) {
        parameter("grant_type", GrantType.DEVICE)
        parameter("code", code)
        parameter("client_id", clientId)
        parameter("client_secret", clientSecret)
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
suspend fun BaiduAuthClient.getRefreshToken(): AuthorizeAccessToken = useHttpClient { client ->
    client.post(AuthorizeApi.TOKEN) {
        parameter("grant_type", GrantType.REFRESH)
        parameter("refresh_token", refreshToken)
        parameter("client_id", clientId)
        parameter("client_secret", clientSecret)
    }
}