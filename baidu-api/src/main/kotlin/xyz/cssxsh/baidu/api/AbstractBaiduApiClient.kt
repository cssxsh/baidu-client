package xyz.cssxsh.baidu.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.*

public abstract class AbstractBaiduApiClient<C> : BaiduApiClient<C> {
    protected open val cookiesStorage: CookiesStorage = AcceptAllCookiesStorage()

    protected open val timeout: Long = 30_000

    protected open val userAgent: String = "curl/7.61.0"

    protected open val callExceptionHandler: CallRequestExceptionHandler = { _, _ -> }

    protected open val apiIgnore: suspend (Throwable) -> Boolean = { it is IOException }

    protected open val client: HttpClient = HttpClient(OkHttp) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(json = BaiduJson, contentType = ContentType.Application.Json)
            json(json = BaiduJson, contentType = ContentType.Text.Html)
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
            handleResponseExceptionWithRequest(callExceptionHandler)
        }
    }

    public override suspend fun <R> useHttpClient(block: suspend C.(HttpClient) -> R): R {
        return supervisorScope {
            var ignores = 0
            while (isActive) {
                try {
                    return@supervisorScope block.invoke(config, client)
                } catch (throwable: Throwable) {
                    if (isActive && apiIgnore(throwable)) {
                        ignores++
                    } else {
                        throw throwable
                    }
                }
            }
            throw CancellationException("ignores: $ignores")
        }
    }

    public override fun close(): Unit = client.close()
}