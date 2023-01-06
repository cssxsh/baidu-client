package xyz.cssxsh.baidu.oauth

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.baidu.api.*
import xyz.cssxsh.baidu.oauth.data.*

@PublishedApi
internal const val AUTHORIZE: String = "https://openapi.baidu.com/oauth/2.0/authorize"

@PublishedApi
internal const val TOKEN: String = "https://openapi.baidu.com/oauth/2.0/token"

@PublishedApi
internal const val DEVICE_CODE: String = "https://openapi.baidu.com/oauth/2.0/device/code"

@PublishedApi
internal const val DEVICE: String = "https://openapi.baidu.com/device"

@PublishedApi
internal const val DEFAULT_REDIRECT_URL: String = "https://openapi.baidu.com/oauth/2.0/login_success"

@PublishedApi
internal const val DEFAULT_REDIRECT: String = "oob"

@PublishedApi
internal const val ACCESS_EXPIRES: Long = 86400L

@PublishedApi
internal fun ParametersBuilder.appendParameter(key: String, value: Any?) {
    value?.let { append(key, it.toString()) }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 */
@PublishedApi
internal fun BaiduUserAuthClient<*>.getWebAuthorizeUrl(
    type: AuthorizeType,
    state: String? = null,
    display: DisplayType = DisplayType.PAGE,
    force: Boolean? = null,
    confirm: Boolean? = null,
    login: LoginType? = null,
    qrcode: Boolean = true,
    extend: Map<String, Any?>? = null
): Url = with(URLBuilder()) {
    takeFrom(AUTHORIZE)
    parameters.apply {
        appendParameter("client_id", config.appKey)
        appendParameter("response_type", type)
        appendParameter("redirect_uri", redirect)
        appendParameter("scope", scope.joinToString(","))
        appendParameter("display", display)
        appendParameter("state", state)
        appendParameter("force_login", force?.toInt())
        appendParameter("confirm_login", confirm?.toInt())
        appendParameter("login_type", login)
        appendParameter("qrcode", qrcode.toInt())
        for ((name, value) in extend.orEmpty()) {
            appendParameter(name, value)
        }
    }
    build()
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/implicit)
 */
@PublishedApi
internal fun Url.getAuthorizeToken(): AuthorizeAccessToken {
    return if (parameters.isEmpty() && fragment.isNotBlank()) {
        Url(toString().replace('#', '?')).getAuthorizeToken()
    } else {
        AuthorizeAccessToken(
            accessToken = requireNotNull(parameters["access_token"]) { "Not Found access_token" },
            expiresIn = requireNotNull(parameters["expires_in"]) { "Not Found expires_in" }.toLong(),
            refreshToken = parameters["refresh_token"].orEmpty(),
            scope = ScopeType(parameters["scope"].orEmpty()),
            sessionKey = parameters["session_key"].orEmpty(),
            sessionSecret = parameters["session_secret"].orEmpty()
        )
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 */
@PublishedApi
internal fun Url.getAuthorizeCode(): String = parameters["code"].orEmpty()

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 */
@PublishedApi
internal suspend fun BaiduUserAuthClient<*>.getAuthorizeToken(code: String): AuthorizeAccessToken {
    return useHttpClient { client ->
        client.post(TOKEN) {
            parameter("grant_type", GrantType.AUTHORIZATION)
            parameter("code", code)
            parameter("client_id", appKey)
            parameter("client_secret", secretKey)
            parameter("redirect_uri", redirect)
        }.body()
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/client)
 */
@PublishedApi
internal suspend fun BaiduUserAuthClient<*>.getClientCredentialsToken(): AuthorizeAccessToken {
    return useHttpClient { client ->
        client.post(TOKEN) {
            parameter("grant_type", GrantType.CLIENT_CREDENTIALS)
            parameter("client_id", appKey)
            parameter("client_secret", secretKey)
            parameter("scope", scope.joinToString(","))
        }.body()
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/client)
 * @see [getClientCredentialsToken]
 */
@PublishedApi
internal suspend fun BaiduUserAuthClient<*>.getDeveloperCredentialsToken(): AuthorizeAccessToken {
    return useHttpClient { client ->
        client.post(TOKEN) {
            parameter("grant_type", GrantType.DEVELOPER_CREDENTIALS)
            parameter("client_id", appKey)
            parameter("client_secret", secretKey)
            parameter("scope", scope.joinToString(","))
        }.body()
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
@PublishedApi
internal suspend fun BaiduUserAuthClient<*>.getDeviceCode(): AuthorizeDeviceCode {
    return useHttpClient { client ->
        client.post(DEVICE_CODE) {
            parameter("client_id", appKey)
            parameter("response_type", "device_code")
            parameter("scope", scope.joinToString(","))
        }.body()
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
@PublishedApi
internal fun getDeviceAuthorizeUrl(
    code: String,
    display: DisplayType = DisplayType.PAGE,
    force: Boolean? = null,
    redirect: String? = null,
    extend: Map<String, Any?>? = null
): Url = with(URLBuilder()) {
    takeFrom(AUTHORIZE)
    parameters.apply {
        appendParameter("code", code)
        appendParameter("display", display)
        appendParameter("force_login", force?.toInt())
        appendParameter("redirect_uri", redirect)
        for ((name, value) in extend.orEmpty()) {
            appendParameter(name, value)
        }
    }
    build()
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
@PublishedApi
internal suspend fun BaiduUserAuthClient<*>.getDeviceToken(code: String): AuthorizeAccessToken {
    return useHttpClient { client ->
        client.post(TOKEN) {
            parameter("grant_type", GrantType.DEVICE)
            parameter("code", code)
            parameter("client_id", appKey)
            parameter("client_secret", secretKey)
        }.body()
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
@PublishedApi
internal suspend fun BaiduUserAuthClient<*>.getDeviceQrcode(code: AuthorizeDeviceCode): ByteArray {
    return useHttpClient { client ->
        client.get(code.qrcodeUrl) {
            parameter("grant_type", GrantType.DEVICE)
            parameter("client_id", appKey)
            parameter("client_secret", secretKey)
        }.body()
    }
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
@PublishedApi
internal suspend fun BaiduUserAuthClient<*>.getRefreshToken(): AuthorizeAccessToken {
    return useHttpClient { client ->
        client.post(TOKEN) {
            parameter("grant_type", GrantType.REFRESH)
            parameter("refresh_token", refreshToken())
            parameter("client_id", appKey)
            parameter("client_secret", secretKey)
        }.body()
    }
}