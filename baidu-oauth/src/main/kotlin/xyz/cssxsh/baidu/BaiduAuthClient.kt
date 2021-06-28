package xyz.cssxsh.baidu

import io.ktor.client.*
import xyz.cssxsh.baidu.oauth.ScopeType

interface BaiduAuthClient: BaiduAuthConfig {

    suspend fun <R> useHttpClient(block: suspend BaiduAuthClient.(HttpClient) -> R): R

    override val appName: String

    override val appId: Long

    override val appKey: String

    override val secretKey: String

    val accessToken: String

    val refreshToken: String

    val redirect: String

    val scope: List<ScopeType>
}