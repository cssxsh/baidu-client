package xyz.cssxsh.baidu.oauth.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.oauth.*
import java.time.*

/**
 * 百度认证数据
 */
@Serializable
public data class BaiduAuthTokenData(
    @Contextual
    @SerialName("expires")
    override var expires: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("access_token")
    override var accessTokenValue: String = "",
    @SerialName("refresh_token")
    override var refreshTokenValue: String = "",
    @SerialName("scope")
    override var scope: List<String> = emptyList()
) : BaiduAuthStatus