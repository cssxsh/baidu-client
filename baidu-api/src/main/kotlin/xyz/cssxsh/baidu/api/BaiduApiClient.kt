package xyz.cssxsh.baidu.api

import io.ktor.client.*
import io.ktor.utils.io.core.*

/**
 * 百度 API 客户端基本接口
 */
public interface BaiduApiClient<C> : Closeable {

    public val config: C

    public suspend fun <R> useHttpClient(block: suspend C.(HttpClient) -> R): R
}