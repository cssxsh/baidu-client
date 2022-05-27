package xyz.cssxsh.baidu.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.*

public abstract class AbstractBaiduApiClient<C> : BaiduApiClient<C> {
    protected open val cookiesStorage: CookiesStorage = AcceptAllCookiesStorage()

    protected open val timeout: Long = 30_000

    protected open val userAgent: String = "curl/7.61.0"

    protected open val callExceptionHandler: CallExceptionHandler = {}

    protected open val apiIgnore: suspend (Throwable) -> Boolean = { it is IOException }

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
            handleResponseException(callExceptionHandler)
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