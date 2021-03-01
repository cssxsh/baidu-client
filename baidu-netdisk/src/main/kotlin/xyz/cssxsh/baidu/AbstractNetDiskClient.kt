package xyz.cssxsh.baidu

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
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
            agent = "pan.baidu.com"
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
    protected var accessTokenValue: String? = null

    @Suppress("unused")
    protected var expires: OffsetDateTime = OffsetDateTime.now()

    @Suppress("unused")
    protected var refreshTokenValue: String? = null

    override val accessToken: String
        get() = requireNotNull(accessTokenValue?.takeIf { expires >= OffsetDateTime.now() })

    override val refreshToken: String
        get() = requireNotNull(refreshTokenValue)

    internal abstract val appKey: String

    internal abstract val appName: String

    internal abstract val appId: Int

    internal open val appDataFolder: String
        get() = "/apps/$appName"

    override val clientId: String
        get() = appKey

    override val clientSecret: String
        get() = throw IllegalAccessError()

    @Suppress("unused")
    protected fun AuthorizeAccessToken.save(): Unit = synchronized(expires) {
        accessTokenValue = accessToken
        refreshTokenValue = refreshToken
        expires = OffsetDateTime.now().plusMinutes(expiresIn)
    }

    fun setToken(token: String) = synchronized(expires) {
        accessTokenValue = token
        expires = OffsetDateTime.now().plusMinutes(AuthorizeApi.ACCESS_EXPIRES)
    }

    suspend fun authorize(block: suspend (Url) -> String) {
        getAuthorizeToken(code = block(getWebAuthorizeUrl(type = AuthorizeType.AUTHORIZATION))).save()
    }

    suspend fun implicit(block: suspend (Url) -> String) {
        setToken(token = block(getWebAuthorizeUrl(type = AuthorizeType.AUTHORIZATION)))
    }

    suspend fun credentials() {
        getCredentialsToken().save()
    }

    suspend fun device(block: suspend (Url, String) -> Unit) {
        getDeviceCode().run {
            block(getDeviceAuthorizeUrl(code = deviceCode), userCode)
            getDeviceToken(code = deviceCode).save()
        }
    }

    suspend fun refresh() {
        getRefreshToken().save()
    }
}