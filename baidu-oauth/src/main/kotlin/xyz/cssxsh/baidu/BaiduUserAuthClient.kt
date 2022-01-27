package xyz.cssxsh.baidu

import io.ktor.http.*
import kotlinx.coroutines.*
import xyz.cssxsh.baidu.oauth.*
import java.time.*

public interface BaiduUserAuthClient : BaiduAuthClient {
    public companion object {
        public const val TIMEOUT: Long = 10 * 60 * 1000L
    }

    /**
     * 服务端的方式获取 Token, block 输入 认证网页 Url ，返回认证码
     */
    public suspend fun authorize(timeout: Long = TIMEOUT, block: suspend (Url) -> String): AuthorizeAccessToken {
        val time = OffsetDateTime.now()
        val token = withTimeout(timeout) {
            getAuthorizeToken(code = block(getWebAuthorizeUrl(type = AuthorizeType.AUTHORIZATION)))
        }
        saveToken(token = token, time = time)
        return token
    }

    /**
     * 移动端的方式获取 Token, block 输入 认证网页 Url ，返回跳转Url
     */
    public suspend fun implicit(timeout: Long = TIMEOUT, block: suspend (Url) -> Url): AuthorizeAccessToken {
        val time = OffsetDateTime.now()
        val token = withTimeout(timeout) {
            block(getWebAuthorizeUrl(type = AuthorizeType.IMPLICIT)).getAuthorizeToken()
        }
        saveToken(token = token, time = time)
        return token
    }

    /**
     * 获取临时 Token，没有用户授权
     */
    public suspend fun credentials(): AuthorizeAccessToken {
        val time = OffsetDateTime.now()
        val token = getClientCredentialsToken()
        saveToken(token = token, time = time)
        return token
    }

    /**
     * 获取临时 Token，没有用户授权
     */
    public suspend fun developer(): AuthorizeAccessToken {
        val time = OffsetDateTime.now()
        val token = getDeveloperCredentialsToken()
        saveToken(token = token, time = time)
        return token
    }

    /**
     * Client Credentials 授权，仅得到部分授权
     */
    @Deprecated(message = "BaiduUserAuthClient Power By User.", replaceWith = ReplaceWith("credentials()"))
    override suspend fun token(): AuthorizeAccessToken = credentials()

    /**
     * 设备认证的方式获取 Token, block 第一个参数是 直接网页认证的Url，第二个是 二维码认证的图片Url
     */
    public suspend fun device(block: suspend (Url, Url) -> Unit): AuthorizeAccessToken {
        val time = OffsetDateTime.now()
        val code = getDeviceCode()
        val token = withTimeout(code.expiresIn * 1000L) {
            launch { block(getDeviceAuthorizeUrl(code = code.userCode), Url(code.qrcodeUrl)) }
            while (isActive) {
                runCatching {
                    getDeviceToken(code = code.deviceCode)
                }.onFailure {
                    when ((it as? AuthorizeException)?.type) {
                        AuthorizeErrorType.AUTHORIZATION_PENDING, AuthorizeErrorType.SLOW_DOWN -> {
                            delay(code.interval * 1000L)
                        }
                        else -> throw it
                    }
                }.onSuccess {
                    return@withTimeout it
                }
            }
            throw CancellationException()
        }
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