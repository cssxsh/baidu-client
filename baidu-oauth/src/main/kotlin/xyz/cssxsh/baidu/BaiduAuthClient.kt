package xyz.cssxsh.baidu

import io.ktor.client.*
import xyz.cssxsh.baidu.oauth.*

interface BaiduAuthClient : BaiduAuthConfig, BaiduAuthToken {

    suspend fun <R> useHttpClient(block: suspend BaiduAuthClient.(HttpClient) -> R): R

    val redirect: String get() = DEFAULT_REDIRECT

    val scope: List<ScopeType>

    suspend fun saveToken(token: AuthorizeAccessToken)

    suspend fun token(): AuthorizeAccessToken

    suspend fun refresh(): AuthorizeAccessToken
}