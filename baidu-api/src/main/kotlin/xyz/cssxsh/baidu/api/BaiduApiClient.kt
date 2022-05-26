package xyz.cssxsh.baidu.api

import io.ktor.client.*
import io.ktor.utils.io.core.*

public interface BaiduApiClient<C> : Closeable {

    public val config: C

    public suspend fun <R> useHttpClient(block: suspend C.(HttpClient) -> R): R
}