package xyz.cssxsh.baidu.oauth

import kotlinx.serialization.*
import java.time.*

@Serializable
public data class AuthorizeAccessToken(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: Long,
    @SerialName("refresh_token")
    val refreshToken: String = "",
    @SerialName("scope")
    @Serializable(with = ScopesSerializer::class)
    val scope: List<ScopeType>,
    @SerialName("session_key")
    val sessionKey: String,
    @SerialName("session_secret")
    val sessionSecret: String
) {
    @Transient
    internal var start: OffsetDateTime = OffsetDateTime.now()

    public val expires: OffsetDateTime get() = start.plusSeconds(expiresIn)
}