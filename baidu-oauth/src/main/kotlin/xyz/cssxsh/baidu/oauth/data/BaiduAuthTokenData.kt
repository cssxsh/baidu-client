package xyz.cssxsh.baidu.oauth.data

import xyz.cssxsh.baidu.oauth.*
import java.time.*

/**
 * 百度认证数据
 */
public data class BaiduAuthTokenData(
    override var expires: OffsetDateTime = OffsetDateTime.MIN,
    override var accessTokenValue: String = "",
    override var refreshTokenValue: String = "",
    override var scope: List<String> = emptyList()
) : BaiduAuthStatus