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
import xyz.cssxsh.baidu.exception.*
import xyz.cssxsh.baidu.oauth.*
import java.io.*
import java.time.*

public abstract class AbstractBaiduAuthClient : BaiduAuthClient, Closeable {
    @Suppress("unused")
    protected open val cookiesStorage: CookiesStorage = AcceptAllCookiesStorage()

    protected open val timeout: Long get() = BaiduUserAuthClient.TIMEOUT

    protected open val userAgent: String = "curl/7.61.0"

    protected open val client: HttpClient = HttpClient(OkHttp) {
        Json {
            serializer = KotlinxSerializer(BaiduJson)
            accept(ContentType.Text.Html)
        }
        ContentEncoding()
        install(UserAgent) {
            agent = userAgent
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
            requestTimeoutMillis = null
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

    public override fun close(): Unit = client.close()

    protected open val apiIgnore: suspend (Throwable) -> Boolean = { it is IOException }

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

    override var scope: List<ScopeType> = listOf(ScopeType.PUBLIC)
        protected set

    @Suppress("unused")
    override var accessTokenValue: String = ""
        protected set

    @Suppress("unused")
    override var refreshTokenValue: String = ""
        protected set

    @Suppress("unused")
    override var expires: OffsetDateTime = OffsetDateTime.MIN
        protected set

    @Suppress("unused")
    protected val mutex: Mutex = Mutex()

    override suspend fun accessToken(): String {
        return accessTokenValue.takeIf { expires >= OffsetDateTime.now() && it.isNotBlank() }
            ?: throw NotTokenException("AccessToken", this)
    }

    override suspend fun refreshToken(): String {
        return refreshTokenValue.takeIf { it.isNotBlank() }
            ?: throw NotTokenException("RefreshToken", this)
    }

    protected fun save(token: AuthorizeAccessToken, time: OffsetDateTime) {
        accessTokenValue = token.accessToken
        refreshTokenValue = token.refreshToken
        expires = time.plusSeconds(token.expiresIn)
        scope = token.scope
    }

    override suspend fun saveToken(token: AuthorizeAccessToken, time: OffsetDateTime): Unit = mutex.withLock {
        save(token, time)
    }
}