package xyz.cssxsh.baidu

import io.ktor.client.*
import io.ktor.http.*
import kotlinx.coroutines.*
import xyz.cssxsh.baidu.oauth.*

interface BaiduAuthClient : BaiduAuthConfig {

    suspend fun <R> useHttpClient(block: suspend BaiduAuthClient.(HttpClient) -> R): R

    override val appName: String

    override val appId: Long

    override val appKey: String

    override val secretKey: String

    val accessToken: String

    val refreshToken: String

    val redirect: String

    val scope: List<ScopeType>

    suspend fun saveToken(token: AuthorizeAccessToken)

    /**
     * 服务端的方式获取 Token, block 输入 认证网页 Url ，返回认证码
     */
    suspend fun authorize(block: suspend (Url) -> String) = withTimeout(10 * 60 * 1000L) {
        val token = getAuthorizeToken(code = block(getWebAuthorizeUrl(type = AuthorizeType.AUTHORIZATION)))
        saveToken(token = token)
    }

    /**
     * 移动端的方式获取 Token, block 输入 认证网页 Url ，返回跳转Url
     */
    suspend fun implicit(block: suspend (Url) -> Url) = withTimeout(10 * 60 * 1000L) {
        saveToken(token = block(getWebAuthorizeUrl(type = AuthorizeType.IMPLICIT)).getAuthorizeToken())
    }

    /**
     * 获取临时 Token，莫得意义
     */
    suspend fun credentials() = saveToken(token = getClientCredentialsToken())

    /**
     * 获取临时 Token，莫得意义
     */
    suspend fun developer() = saveToken(token = getDeveloperCredentialsToken())

    /**
     * 设备认证的方式获取 Token, block 第一个参数是 直接网页认证的Url，第二个是 二维码认证的图片Url
     */
    suspend fun device(block: suspend (Url, Url) -> Unit) = saveToken(token = getDeviceCode().let { code ->
        withTimeout(code.expiresIn * 1000L) {
            println(code)
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
    })

    /**
     * 刷新 Token
     */
    suspend fun refresh() = saveToken(token = getRefreshToken())
}