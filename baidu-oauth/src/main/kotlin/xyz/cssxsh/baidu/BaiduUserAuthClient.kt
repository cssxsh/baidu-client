package xyz.cssxsh.baidu

import io.ktor.http.*
import kotlinx.coroutines.*
import xyz.cssxsh.baidu.oauth.*

public interface BaiduUserAuthClient : BaiduAuthClient {
    public companion object {
        public const val TIMEOUT: Long = 10 * 60 * 1000L
    }

    /**
     * 服务端的方式获取 Token, block 输入 认证网页 Url ，返回认证码
     */
    public suspend fun authorize(timeout: Long = TIMEOUT, block: suspend (Url) -> String): AuthorizeAccessToken = save {
        withTimeout(timeout) {
            getAuthorizeToken(code = block(getWebAuthorizeUrl(type = AuthorizeType.AUTHORIZATION)))
        }
    }

    /**
     * 移动端的方式获取 Token, block 输入 认证网页 Url ，返回跳转Url
     */
    public suspend fun implicit(timeout: Long = TIMEOUT, block: suspend (Url) -> Url): AuthorizeAccessToken = save {
        withTimeout(timeout) {
            block(getWebAuthorizeUrl(type = AuthorizeType.IMPLICIT)).getAuthorizeToken()
        }
    }

    /**
     * 获取临时 Token，没有用户授权
     */
    public suspend fun credentials(): AuthorizeAccessToken = save { getClientCredentialsToken() }

    /**
     * 获取临时 Token，没有用户授权
     */
    public suspend fun developer(): AuthorizeAccessToken = save { getDeveloperCredentialsToken() }

    /**
     * Client Credentials 授权，仅得到部分授权
     */
    @Deprecated(message = "BaiduUserAuthClient Power By User.", replaceWith = ReplaceWith("credentials()"))
    override suspend fun token(): AuthorizeAccessToken = credentials()

    /**
     * 设备认证的方式获取 Token, block 第一个参数是 直接网页认证的Url，第二个是 二维码认证的图片Url
     */
    public suspend fun device(block: suspend (Url, Url) -> Unit): AuthorizeAccessToken = save {
        val code = getDeviceCode()
        withTimeout(code.expiresIn * 1000L) {
            launch { block(getDeviceAuthorizeUrl(code = code.userCode), Url(code.qrcodeUrl)) }
            while (isActive) {
                try {
                    return@withTimeout getDeviceToken(code = code.deviceCode)
                } catch (cause: AuthorizeException) {
                    when (cause.type) {
                        AuthorizeErrorType.AUTHORIZATION_PENDING, AuthorizeErrorType.SLOW_DOWN -> {
                            delay(code.interval * 1000L)
                        }
                        else -> throw cause
                    }
                }
            }
            throw CancellationException()
        }
    }

    /**
     * 刷新 Token
     */
    override suspend fun refresh(): AuthorizeAccessToken = save { getRefreshToken() }
}