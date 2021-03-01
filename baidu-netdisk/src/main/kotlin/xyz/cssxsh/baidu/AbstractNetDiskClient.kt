package xyz.cssxsh.baidu

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.date.*
import kotlinx.coroutines.*
import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.disk.*
import java.io.IOException
import java.time.OffsetDateTime
import kotlin.time.*

abstract class AbstractNetDiskClient(cookies: List<HttpCookie>) : BaiduAuthClient {
    @Suppress("unused")
    val cookiesStorage = AcceptAllCookiesStorage()

    @Suppress("unused")
    suspend fun loadCookies(cookies: List<HttpCookie>) = cookies.forEach { cookie ->
        cookiesStorage.addCookie(
            NetDiskApi.INDEX_PAGE, Cookie(
                name = cookie.name,
                value = cookie.value,
                expires = cookie.expirationDate?.let { GMTDate((it * 1000).toLong()) },
                path = cookie.path,
                domain = cookie.domain,
                secure = cookie.secure,
                httpOnly = cookie.httpOnly
            )
        )
    }

    init {
        runBlocking {
            loadCookies(cookies)
        }
    }

    protected open fun client(): HttpClient = HttpClient(OkHttp) {
        Json {
            serializer = KotlinxSerializer()
            accept(ContentType.Text.Html)
        }
        BrowserUserAgent()
        install(UserAgent) {
            agent = NetDiskApi.USER_AGENT
        }
        ContentEncoding {
            gzip()
            deflate()
            identity()
        }
        install(HttpCookies) {
            storage = cookiesStorage
        }
        install(HttpTimeout) {
            socketTimeoutMillis = (5).minutes.toLongMilliseconds()
            connectTimeoutMillis = (5).minutes.toLongMilliseconds()
            requestTimeoutMillis = (5).minutes.toLongMilliseconds()
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

    override val redirect: String = AuthorizeApi.DEFAULT_REDIRECT

    @Suppress("unused")
    protected open var accessTokenValue: String? = null

    @Suppress("unused")
    protected open var expires: OffsetDateTime = OffsetDateTime.now()

    @Suppress("unused")
    protected open var refreshTokenValue: String? = null

    override val accessToken: String
        get() = requireNotNull(accessTokenValue?.takeIf { expires >= OffsetDateTime.now() && it.isNotBlank() })

    override val refreshToken: String
        get() = requireNotNull(refreshTokenValue?.takeIf { it.isNotBlank() })

    open val appDataFolder: String
        get() = "/apps/$appName"

    open fun saveToken(token: AuthorizeAccessToken): Unit = synchronized(expires) {
        accessTokenValue = token.accessToken
        refreshTokenValue = token.refreshToken
        expires = OffsetDateTime.now().plusSeconds(token.expiresIn)
    }

    fun setToken(token: String, expiresIn: SecondUnit = AuthorizeApi.ACCESS_EXPIRES): Unit = synchronized(expires) {
        accessTokenValue = token
        expires = OffsetDateTime.now().plusSeconds(expiresIn)
    }

    suspend fun authorize(block: suspend (Url) -> String) =
        saveToken(token = getAuthorizeToken(code = block(getWebAuthorizeUrl(type = AuthorizeType.AUTHORIZATION))))

    suspend fun implicit(block: suspend (Url) -> Url) =
        saveToken(token = block(getWebAuthorizeUrl(type = AuthorizeType.IMPLICIT)).getAuthorizeToken())

    suspend fun credentials() =
        saveToken(token = getCredentialsToken())

    private suspend fun AuthorizeDeviceCode.wait(): AuthorizeAccessToken = withTimeout(expiresIn.seconds) {
        var tokens: AuthorizeAccessToken? = null
        while (isActive && tokens == null) {
            tokens = runCatching {
                getDeviceToken(code = deviceCode)
            }.onFailure {
                if (it is AuthorizeException) {
                    when(it.type) {
                        AuthorizeErrorType.AUTHORIZATION_PENDING -> {
                            delay(interval.seconds)
                        }
                        AuthorizeErrorType.SLOW_DOWN -> {
                            delay(interval.seconds)
                        }
                        else -> throw it
                    }
                } else {
                    throw it
                }
            }.getOrNull()
        }
        tokens!!
    }

    suspend fun device(block: suspend (Url, Url) -> Unit) = saveToken(token = getDeviceCode().let { code ->
        block(getDeviceAuthorizeUrl(code = code.userCode), Url(code.qrcodeUrl))
        code.wait()
    })

    @JvmName("device_")
    suspend fun device(block: suspend (ByteArray) -> Unit) = saveToken(token = getDeviceCode().let { code ->
        block(useHttpClient { it.get(code.qrcodeUrl) })
        code.wait()
    })

    suspend fun refresh() =
        saveToken(token = getRefreshToken())
}