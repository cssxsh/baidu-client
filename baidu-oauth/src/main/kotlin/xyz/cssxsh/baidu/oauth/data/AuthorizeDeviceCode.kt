package xyz.cssxsh.baidu.oauth.data

import kotlinx.serialization.*

@Serializable
public data class AuthorizeDeviceCode(
    @SerialName("device_code")
    val deviceCode: String,
    @SerialName("expires_in")
    val expiresIn: Long,
    @SerialName("interval")
    val interval: Long,
    @SerialName("qrcode_url")
    val qrcodeUrl: String,
    @SerialName("user_code")
    val userCode: String,
    @SerialName("verification_url")
    val verificationUrl: String
)