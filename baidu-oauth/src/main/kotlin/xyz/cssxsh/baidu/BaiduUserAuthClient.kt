package xyz.cssxsh.baidu

import io.ktor.http.*
import kotlinx.coroutines.*
import xyz.cssxsh.baidu.oauth.*

interface BaiduUserAuthClient : BaiduAuthClient {
    companion object {
        const val AUTHORIZE_TIMEOUT = 10 * 60 * 1000L
    }

    /**
     * 服务端的方式获取 Token, block 输入 认证网页 Url ，返回认证码
     */
    suspend fun authorize(timeout: Long = AUTHORIZE_TIMEOUT, block: suspend (Url) -> String): AuthorizeAccessToken {
        val token = withTimeout(timeout) {
            getAuthorizeToken(code = block(getWebAuthorizeUrl(type = AuthorizeType.AUTHORIZATION)))
        }
        saveToken(token = token)
        return token
    }

    /**
     * 移动端的方式获取 Token, block 输入 认证网页 Url ，返回跳转Url
     */
    suspend fun implicit(timeout: Long = AUTHORIZE_TIMEOUT, block: suspend (Url) -> Url): AuthorizeAccessToken {
        val token = withTimeout(timeout) {
            block(getWebAuthorizeUrl(type = AuthorizeType.IMPLICIT)).getAuthorizeToken()
        }
        saveToken(token = token)
        return token
    }

    /**
     * 获取临时 Token，莫得意义
     */
    suspend fun credentials(): AuthorizeAccessToken = getClientCredentialsToken().also { saveToken(token = it) }

    /**
     * 获取临时 Token，莫得意义
     */
    suspend fun developer(): AuthorizeAccessToken = getDeveloperCredentialsToken().also { saveToken(token = it) }

    /**
     * 设备认证的方式获取 Token, block 第一个参数是 直接网页认证的Url，第二个是 二维码认证的图片Url
     */
    suspend fun device(block: suspend (Url, Url) -> Unit): AuthorizeAccessToken {
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
        saveToken(token = token)
        return token
    }

    /**
     * 刷新 Token
     */
    override suspend fun refresh(): AuthorizeAccessToken = getRefreshToken().also { saveToken(token = it) }
}