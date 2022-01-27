package xyz.cssxsh.baidu

import io.ktor.client.*
import xyz.cssxsh.baidu.oauth.*
import java.time.*

public interface BaiduAuthClient : BaiduAuthConfig, BaiduAuthToken {

    public suspend fun <R> useHttpClient(block: suspend BaiduAuthClient.(HttpClient) -> R): R

    public val redirect: String get() = DEFAULT_REDIRECT

    public val scope: List<ScopeType>

    public suspend fun saveToken(token: AuthorizeAccessToken, time: OffsetDateTime)

    public suspend fun token(): AuthorizeAccessToken

    public suspend fun refresh(): AuthorizeAccessToken
}