package xyz.cssxsh.baidu.oauth

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.baidu.*

internal const val AUTHORIZE = "https://openapi.baidu.com/oauth/2.0/authorize"

internal const val TOKEN = "https://openapi.baidu.com/oauth/2.0/token"

internal const val DEVICE_CODE = "https://openapi.baidu.com/oauth/2.0/device/code"

internal const val DEVICE = "https://openapi.baidu.com/device"

internal const val DEFAULT_REDIRECT_URL = "https://openapi.baidu.com/oauth/2.0/login_success"

internal const val DEFAULT_REDIRECT = "oob"

internal const val ACCESS_EXPIRES: Long = 86400L

private typealias AuthClient = BaiduUserAuthClient

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 */
internal fun AuthClient.getWebAuthorizeUrl(
    type: AuthorizeType,
    state: String? = null,
    display: DisplayType = DisplayType.PAGE,
    force: Boolean? = null,
    confirm: Boolean? = null,
    login: LoginType? = null,
    qrcode: Boolean = true,
    extend: Map<String, Any?>? = null
): Url = with(HttpRequestBuilder(AUTHORIZE)) {
    parameter("client_id", appKey)
    parameter("response_type", type)
    parameter("redirect_uri", redirect)
    parameter("scope", scope.joinToString(","))
    parameter("display", display)
    parameter("state", state)
    parameter("force_login", force?.toInt())
    parameter("confirm_login", confirm?.toInt())
    parameter("login_type", login)
    parameter("qrcode", qrcode.toInt())
    for ((name, value) in extend.orEmpty()) {
        parameter(name, value)
    }
    url.build()
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/implicit)
 */
internal fun Url.getAuthorizeToken(): AuthorizeAccessToken {
    return if (parameters.isEmpty() && fragment.isNotBlank()) {
        Url(toString().replace('#', '?')).getAuthorizeToken()
    } else {
        AuthorizeAccessToken(
            accessToken = requireNotNull(parameters["access_token"]) { "Not Found access_token" },
            expiresIn = requireNotNull(parameters["expires_in"]) { "Not Found expires_in" }.toLong(),
            refreshToken = parameters["refresh_token"].orEmpty(),
            scope = ScopesSerializer.splitScope(parameters["scope"].orEmpty()),
            sessionKey = parameters["session_key"].orEmpty(),
            sessionSecret = parameters["session_secret"].orEmpty()
        )
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 */
internal fun Url.getAuthorizeCode(): String = parameters["code"].orEmpty()

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 */
internal suspend fun AuthClient.getAuthorizeToken(code: String): AuthorizeAccessToken = useHttpClient { client ->
    client.post(TOKEN) {
        parameter("grant_type", GrantType.AUTHORIZATION)
        parameter("code", code)
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
        parameter("redirect_uri", redirect)
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/client)
 */
internal suspend fun AuthClient.getClientCredentialsToken(): AuthorizeAccessToken = useHttpClient { client ->
    client.post(TOKEN) {
        parameter("grant_type", GrantType.CLIENT_CREDENTIALS)
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
        parameter("scope", scope.joinToString(","))
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/client)
 * @see [getClientCredentialsToken]
 */
internal suspend fun AuthClient.getDeveloperCredentialsToken(): AuthorizeAccessToken = useHttpClient { client ->
    client.post(TOKEN) {
        parameter("grant_type", GrantType.DEVELOPER_CREDENTIALS)
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
        parameter("scope", scope.joinToString(","))
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
internal suspend fun AuthClient.getDeviceCode(): AuthorizeDeviceCode = useHttpClient { client ->
    client.post(DEVICE_CODE) {
        parameter("client_id", appKey)
        parameter("response_type", "device_code")
        parameter("scope", scope.joinToString(","))
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
internal fun getDeviceAuthorizeUrl(
    code: String,
    display: DisplayType = DisplayType.PAGE,
    force: Boolean? = null,
    redirect: String? = null,
    extend: Map<String, Any?>? = null
): Url = with(HttpRequestBuilder(AUTHORIZE)) {
    parameter("code", code)
    parameter("display", display)
    parameter("force_login", force?.toInt())
    parameter("redirect_uri", redirect)
    for ((name, value) in extend.orEmpty()) {
        parameter(name, value)
    }
    url.build()
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
internal suspend fun AuthClient.getDeviceToken(code: String): AuthorizeAccessToken = useHttpClient { client ->
    client.post(TOKEN) {
        parameter("grant_type", GrantType.DEVICE)
        parameter("code", code)
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
internal suspend fun AuthClient.getDeviceQrcode(code: AuthorizeDeviceCode): ByteArray = useHttpClient { client ->
    client.get(code.qrcodeUrl) {
        parameter("grant_type", GrantType.DEVICE)
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
internal suspend fun AuthClient.getRefreshToken(): AuthorizeAccessToken = useHttpClient { client ->
    client.post(TOKEN) {
        parameter("grant_type", GrantType.REFRESH)
        parameter("refresh_token", refreshToken)
        parameter("client_id", appKey)
        parameter("client_secret", secretKey)
    }
}