package xyz.cssxsh.baidu

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.disk.*
import java.io.IOException
import java.time.OffsetDateTime

abstract class AbstractNetDiskClient : NetDiskClient {
    @Suppress("unused")
    val cookiesStorage = AcceptAllCookiesStorage()

    protected open fun client(): HttpClient = HttpClient(OkHttp) {
        Json {
            serializer = KotlinxSerializer()
            accept(ContentType.Text.Html)
        }
        BrowserUserAgent()
        install(UserAgent) {
            agent = USER_AGENT
        }
        ContentEncoding {
            gzip()
            deflate()
            identity()
        }
        install(HttpCookies) {
            storage = cookiesStorage
        }
        install(HttpRedirect) {
            allowHttpsDowngrade = true
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 5 * 60 * 1000L
            connectTimeoutMillis = 5 * 60 * 1000L
            requestTimeoutMillis = 5 * 60 * 1000L
        }
        HttpResponseValidator {
            handleResponseException { cause ->
                if (cause is ClientRequestException) {
                    cause.toAuthorizeExceptionOrNull()?.let {
                        throw it
                    }
                }
            }
        }
    }

    open val apiIgnore: suspend (Throwable) -> Boolean = { it is IOException }

    override suspend fun <R> useHttpClient(
        block: suspend BaiduAuthClient.(HttpClient) -> R,
    ): R = withContext(Dispatchers.IO) {
        client().use { client ->
            runCatching {
                block(client)
            }.getOrElse { throwable ->
                if (isActive && apiIgnore(throwable)) {
                    useHttpClient(block = block)
                } else {
                    throw throwable
                }
            }
        }
    }

    override val scope = listOf(
        ScopeType.BASIC,
        ScopeType.NET_DISK
    )

    override val redirect: String = DEFAULT_REDIRECT

    @Suppress("unused")
    protected open var accessTokenValue: String? = null

    @Suppress("unused")
    open var expires: OffsetDateTime = OffsetDateTime.now()
        protected set

    @Suppress("unused")
    protected open var refreshTokenValue: String? = null

    @Suppress("unused")
    protected val mutex = Mutex()

    override val accessToken: String
        get() = requireNotNull(accessTokenValue?.takeIf { expires >= OffsetDateTime.now() && it.isNotBlank() })

    override val refreshToken: String
        get() = requireNotNull(refreshTokenValue?.takeIf { it.isNotBlank() })

    override val appDataFolder: String
        get() = "/apps/$appName"

    open suspend fun saveToken(token: AuthorizeAccessToken): Unit = mutex.withLock {
        accessTokenValue = token.accessToken
        refreshTokenValue = token.refreshToken
        expires = OffsetDateTime.now().plusSeconds(token.expiresIn)
    }

    suspend fun setToken(token: String, expiresIn: SecondUnit = ACCESS_EXPIRES): Unit = mutex.withLock {
        accessTokenValue = token
        expires = OffsetDateTime.now().plusSeconds(expiresIn)
    }

    /**
     * 服务端的方式获取 Token, block 输入 认证网页 Url ，返回认证码
     */
    suspend fun authorize(block: suspend (Url) -> String) =
        saveToken(token = getAuthorizeToken(code = block(getWebAuthorizeUrl(type = AuthorizeType.AUTHORIZATION))))

    /**
     * 移动端的方式获取 Token, block 输入 认证网页 Url ，返回跳转Url
     */
    suspend fun implicit(block: suspend (Url) -> Url) =
        saveToken(token = block(getWebAuthorizeUrl(type = AuthorizeType.IMPLICIT)).getAuthorizeToken())

    /**
     * 获取临时 Token，莫得意义
     */
    suspend fun credentials() = saveToken(token = getCredentialsToken())

    private suspend fun AuthorizeDeviceCode.wait(): AuthorizeAccessToken = withTimeout(expiresIn * 1000L) {
        var tokens: AuthorizeAccessToken? = null
        while (isActive && tokens == null) {
            tokens = runCatching {
                getDeviceToken(code = deviceCode)
            }.onFailure {
                if (it is AuthorizeException) {
                    when (it.type) {
                        AuthorizeErrorType.AUTHORIZATION_PENDING -> {
                            delay(interval * 1000L)
                        }
                        AuthorizeErrorType.SLOW_DOWN -> {
                            delay(interval * 1000L)
                        }
                        else -> throw it
                    }
                } else {
                    throw it
                }
            }.getOrNull()
        }
        tokens ?: throw CancellationException()
    }

    /**
     * 设备认证的方式获取 Token, block 第一个参数是 直接网页认证的Url，第二个是 二维码认证的图片Url
     */
    suspend fun device(block: suspend (Url, Url) -> Unit) = saveToken(token = getDeviceCode().let { code ->
        block(getDeviceAuthorizeUrl(code = code.userCode), Url(code.qrcodeUrl))
        code.wait()
    })

    /**
     * 刷新 Token
     */
    suspend fun refresh() = saveToken(token = getRefreshToken())
}