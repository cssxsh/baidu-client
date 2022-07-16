package xyz.cssxsh.baidu.aip

import xyz.cssxsh.baidu.oauth.*

/**
 * * [AIP 文档](https://ai.baidu.com/ai-doc)
 * * [Token 认证](https://ai.baidu.com/ai-doc/REFERENCE/Ck3dwjhhu)
 */
public interface AipClient : BaiduAuthClient<BaiduAuthConfig> {

    public companion object {
        public const val TIMEOUT: Long = 30 * 1000L
    }

    /**
     * 获取 Token
     */
    override suspend fun token(): AuthorizeAccessToken = save { getClientCredentialsToken() }

    /**
     * 刷新 Token
     */
    override suspend fun refresh(): AuthorizeAccessToken = token()
}