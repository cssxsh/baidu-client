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
import kotlinx.coroutines.sync.*
import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.disk.*
import java.io.*
import java.time.*

abstract class AbstractNetDiskClient : NetDiskClient, Closeable {
    @Suppress("unused")
    val cookiesStorage = AcceptAllCookiesStorage()

    protected open val client: HttpClient by lazy {
        HttpClient(OkHttp) {
            Json {
                serializer = KotlinxSerializer(NetDiskClient.Json)
                accept(ContentType.Text.Html)
            }
            install(UserAgent) {
                agent = USER_AGENT
            }
            ContentEncoding()
            install(HttpCookies) {
                storage = cookiesStorage
            }
            install(HttpRedirect) {
                allowHttpsDowngrade = true
            }
            install(HttpTimeout) {
                socketTimeoutMillis = NetDiskClient.Timeout
                connectTimeoutMillis = NetDiskClient.Timeout
                requestTimeoutMillis = NetDiskClient.Timeout
            }
            HttpResponseValidator {
                handleResponseException { cause ->
                    if (cause is ClientRequestException) {
                        throw try {
                            AuthorizeException(cause)
                        } catch (cause: Throwable) {
                            return@handleResponseException
                        }
                    }
                }
            }
        }
    }

    override fun close() = client.close()

    open val apiIgnore: suspend (Throwable) -> Boolean = { it is IOException }

    override suspend fun <R> useHttpClient(block: suspend BaiduAuthClient.(HttpClient) -> R): R = supervisorScope {
        while (isActive) {
            try {
                return@supervisorScope block(client)
            } catch (throwable: Throwable) {
                if (isActive && apiIgnore(throwable)) {
                    //
                } else {
                    throw throwable
                }
            }
        }
        throw CancellationException()
    }

    override val scope = listOf(ScopeType.BASIC, ScopeType.NET_DISK)

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

    protected fun save(token: AuthorizeAccessToken) {
        accessTokenValue = token.accessToken
        refreshTokenValue = token.refreshToken
        expires = OffsetDateTime.now().plusSeconds(token.expiresIn)
    }

    override suspend fun saveToken(token: AuthorizeAccessToken): Unit = mutex.withLock { save(token) }
}