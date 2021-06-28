package xyz.cssxsh.baidu

import io.ktor.client.*
import xyz.cssxsh.baidu.oauth.ScopeType

interface BaiduAuthClient {

    suspend fun <R> useHttpClient(block: suspend BaiduAuthClient.(HttpClient) -> R): R

    val appName: String

    val appId: Long

    val appKey: String

    val secretKey: String

    val accessToken: String

    val refreshToken: String

    val redirect: String

    val scope: List<ScopeType>
}