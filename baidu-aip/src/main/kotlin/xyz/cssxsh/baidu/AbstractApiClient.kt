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
import java.io.*
import java.time.*

abstract class AbstractApiClient : AipClient, Closeable {
    @Suppress("unused")
    val cookiesStorage = AcceptAllCookiesStorage()

    protected open val client: HttpClient by lazy {
        HttpClient(OkHttp) {
            Json {
                serializer = KotlinxSerializer(AipClient.Json)
                accept(ContentType.Text.Html)
            }
            CurlUserAgent()
            ContentEncoding()
            install(HttpCookies) {
                storage = cookiesStorage
            }
            install(HttpRedirect) {
                allowHttpsDowngrade = true
            }
            install(HttpTimeout) {
                socketTimeoutMillis = AipClient.Timeout
                connectTimeoutMillis = AipClient.Timeout
                requestTimeoutMillis = AipClient.Timeout
            }
            // TODO openapi.baidu.com -> aip.baidubce.com
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

    override var scope = listOf(ScopeType.PUBLIC, ScopeType.WISE_ADAPT)
        protected set

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

    protected fun save(token: AuthorizeAccessToken) {
        accessTokenValue = token.accessToken
        refreshTokenValue = token.refreshToken
        expires = OffsetDateTime.now().plusSeconds(token.expiresIn)
        scope = token.scope
    }

    override suspend fun saveToken(token: AuthorizeAccessToken): Unit = mutex.withLock { save(token) }
}