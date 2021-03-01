package xyz.cssxsh.baidu

import io.ktor.client.*
import xyz.cssxsh.baidu.oauth.ScopeType

interface BaiduAuthClient {

    suspend fun <R> useHttpClient(
        block: suspend BaiduAuthClient.(HttpClient) -> R,
    ): R

    val clientId: String

    val clientSecret: String

    val accessToken: String

    val refreshToken: String

    val redirect: String

    val scope: List<ScopeType>
}