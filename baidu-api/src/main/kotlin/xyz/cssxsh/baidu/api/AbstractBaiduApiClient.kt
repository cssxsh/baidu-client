package xyz.cssxsh.baidu.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.*

/**
 * 百度 API 客户端 抽象实现
 * @property cookiesStorage 保存 cookies
 * @property timeout 网络超时时间
 * @property userAgent Http User Agent
 * @property apiIgnore api exception ignore
 * @property client Http Client
 */
public abstract class AbstractBaiduApiClient<C> : BaiduApiClient<C> {
    protected open val cookiesStorage: CookiesStorage = AcceptAllCookiesStorage()

    protected open val timeout: Long = 30_000

    protected open val userAgent: String = "curl/7.61.0"

    protected open suspend fun callRequestExceptionHandle(cause: Throwable, request: HttpRequest) {
        // ignore
    }

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
            handleResponseExceptionWithRequest(::callRequestExceptionHandle)
        }
    }

    public override suspend fun <R> useHttpClient(block: suspend C.(HttpClient) -> R): R {
        return supervisorScope {
            var ignores = 0
            var cause: Throwable? = null
            while (isActive) {
                try {
                    return@supervisorScope block.invoke(config, client)
                } catch (cancellation: CancellationException) {
                    break
                } catch (throwable: Throwable) {
                    if (isActive && apiIgnore(throwable)) {
                        cause = throwable
                        ignores++
                    } else {
                        throw throwable
                    }
                }
            }
            throw CancellationException("ignores: $ignores", cause)
        }
    }

    public override fun close(): Unit = client.close()
}