package xyz.cssxsh.baidu.oauth

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.baidu.*

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 */
fun BaiduAuthClient.getWebAuthorizeUrl(
    type: AuthorizeType,
    state: String? = null,
    display: DisplayType = DisplayType.PAGE,
    force: Boolean? = null,
    confirm: Boolean? = null,
    login: LoginType? = null,
    extend: Map<String, Any?>? = null
): Url = URLBuilder(AuthorizeApi.AUTHORIZE).apply {
    parameters.apply {
        appendParameter("client_id", appKey)
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
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/implicit)
 */
fun Url.getAuthorizeToken(): AuthorizeAccessToken = AuthorizeAccessToken(
    accessToken = parameters["access_token"].orEmpty(),
    expiresIn = parameters["expires_in"]?.toLong() ?: 0,
    refreshToken = "",
    scope = AuthorizeAccessToken.ScopeSerializer.splitScope(parameters["scope"].orEmpty()),
    sessionKey = parameters["session_key"].orEmpty(),
    sessionSecret = parameters["session_secret"].orEmpty()
)

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 */
fun Url.getAuthorizeCode(): String =
    parameters["code"].orEmpty()

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 */
suspend fun BaiduAuthClient.getAuthorizeToken(code: String): AuthorizeAccessToken = useHttpClient { client ->
    client.post(AuthorizeApi.TOKEN) {
        parameter("grant_type", GrantType.AUTHORIZATION)
        parameter("code", code)
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
        parameter("redirect_uri", redirect)
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/client)
 */
suspend fun BaiduAuthClient.getCredentialsToken(): AuthorizeAccessToken = useHttpClient { client ->
    client.post(AuthorizeApi.TOKEN) {
        parameter("grant_type", GrantType.CREDENTIALS)
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
        parameter("scope", scope.joinToString(","))
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
suspend fun BaiduAuthClient.getDeviceCode(): AuthorizeDeviceCode = useHttpClient { client ->
    client.post(AuthorizeApi.DEVICE_CODE) {
        parameter("client_id", appKey)
        parameter("response_type", "device_code")
        parameter("scope", scope.joinToString(","))
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
fun getDeviceAuthorizeUrl(
    code: String,
    display: DisplayType = DisplayType.PAGE,
    force: Boolean? = null,
    redirect: String? = null,
    extend: Map<String, Any?>? = null
): Url = URLBuilder(AuthorizeApi.DEVICE).apply {
    parameters.apply {
        appendParameter("code", code)
        appendParameter("display", display)
        appendParameter("force_login", force?.toInt())
        appendParameter("redirect_uri", redirect)
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
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
suspend fun BaiduAuthClient.getRefreshToken(): AuthorizeAccessToken = useHttpClient { client ->
    client.post(AuthorizeApi.TOKEN) {
        parameter("grant_type", GrantType.REFRESH)
        parameter("refresh_token", refreshToken)
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
    }
}