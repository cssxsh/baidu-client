package xyz.cssxsh.baidu.oauth

import kotlinx.serialization.*
import xyz.cssxsh.baidu.oauth.data.*
import java.time.*

/**
 * 认证 TOKEN 信息
 */
@Serializable
public data class AuthorizeAccessToken(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: Long,
    @SerialName("refresh_token")
    val refreshToken: String = "",
    @SerialName("scope")
    @Serializable(with = ScopeType::class)
    val scope: List<String>,
    @SerialName("session_key")
    val sessionKey: String,
    @SerialName("session_secret")
    val sessionSecret: String
) {
    @Transient
    @PublishedApi
    internal var start: OffsetDateTime = OffsetDateTime.now()

    public val expires: OffsetDateTime get() = start.plusSeconds(expiresIn)
}