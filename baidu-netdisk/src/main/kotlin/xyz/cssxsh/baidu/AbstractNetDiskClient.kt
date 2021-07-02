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

    val timeout: Long = 30 * 1000L

    protected open fun client(): HttpClient = HttpClient(OkHttp) {
        Json {
            serializer = KotlinxSerializer()
            accept(ContentType.Text.Html)
        }
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
            socketTimeoutMillis = timeout
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
        }
        HttpResponseValidator {
            handleResponseException { cause ->
                throw (cause as? ClientRequestException)?.toAuthorizeExceptionOrNull() ?: return@handleResponseException
            }
        }
    }

    open val apiIgnore: suspend (Throwable) -> Boolean = { it is IOException }

    override suspend fun <R> useHttpClient(block: suspend BaiduAuthClient.(HttpClient) -> R): R = supervisorScope {
        client().use { client ->
            while (isActive) {
                runCatching {
                    block(client)
                }.onFailure { throwable ->
                    if (isActive && apiIgnore(throwable)) {
                        useHttpClient(block = block)
                    } else {
                        throw throwable
                    }
                }.onSuccess {
                    return@supervisorScope it
                }
            }
        }
        throw CancellationException()
    }

    override val scope = listOf(ScopeType.BASIC, ScopeType.NET_DISK)

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

    override suspend fun saveToken(token: AuthorizeAccessToken): Unit = mutex.withLock {
        accessTokenValue = token.accessToken
        refreshTokenValue = token.refreshToken
        expires = OffsetDateTime.now().plusSeconds(token.expiresIn)
    }
}