package xyz.cssxsh.baidu.oauth

import kotlinx.coroutines.sync.*
import xyz.cssxsh.baidu.api.*

public interface BaiduAuthClient<C: BaiduAuthConfig> : BaiduApiClient<C> {

    public val mutex: Mutex

    public val redirect: String get() = DEFAULT_REDIRECT

    public val scope: List<String>

    public suspend fun accessToken(): String

    public suspend fun refreshToken(): String

    public suspend fun save(block: suspend () -> AuthorizeAccessToken): AuthorizeAccessToken

    public suspend fun token(): AuthorizeAccessToken

    public suspend fun refresh(): AuthorizeAccessToken
}