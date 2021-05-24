package xyz.cssxsh.baidu.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.SecondUnit

@Serializable
data class AuthorizeAccessToken(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: SecondUnit,
    @SerialName("refresh_token")
    val refreshToken: String = "",
    @SerialName("scope")
    @Serializable(with = ScopeSerializer::class)
    val scope: List<ScopeType>,
    @SerialName("session_key")
    val sessionKey: String,
    @SerialName("session_secret")
    val sessionSecret: String
)
