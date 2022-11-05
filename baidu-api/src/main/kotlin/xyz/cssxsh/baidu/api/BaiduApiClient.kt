package xyz.cssxsh.baidu.api

import io.ktor.client.*
import io.ktor.utils.io.core.*

/**
 * 百度 API 客户端基本接口
 * @property config 配置
 */
public interface BaiduApiClient<C> : Closeable {

    public val config: C

    /**
     * 使用HTTP客户端
     */
    public suspend fun <R> useHttpClient(block: suspend C.(HttpClient) -> R): R
}