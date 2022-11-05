package xyz.cssxsh.baidu.oauth

import kotlinx.coroutines.sync.*
import xyz.cssxsh.baidu.api.*

/**
 * 百度认证客户端接口
 */
public interface BaiduAuthClient<C : BaiduAuthConfig> : BaiduApiClient<C> {

    /**
     * 同步用的锁
     */
    public val mutex: Mutex

    /**
     * 登录重定向URL
     */
    public val redirect: String get() = DEFAULT_REDIRECT


    /**
     * 权限空间
     */
    public val scope: List<String>

    /**
     * 获取 access token
     */
    public suspend fun accessToken(): String


    /**
     * 获取 refresh token
     */
    public suspend fun refreshToken(): String


    /**
     * 保存 access token
     */
    public suspend fun save(block: suspend () -> AuthorizeAccessToken): AuthorizeAccessToken


    /**
     * 获取 token
     */
    public suspend fun token(): AuthorizeAccessToken


    /**
     * 刷新 token
     */
    public suspend fun refresh(): AuthorizeAccessToken
}