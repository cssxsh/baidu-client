package xyz.cssxsh.baidu.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.SecondUnit

@Serializable
data class AuthorizeDeviceCode(
    @SerialName("device_code")
    val deviceCode: String,
    @SerialName("expires_in")
    val expiresIn: SecondUnit,
    @SerialName("interval")
    val interval: SecondUnit,
    @SerialName("qrcode_url")
    val qrcodeUrl: String,
    @SerialName("user_code")
    val userCode: String,
    @SerialName("verification_url")
    val verificationUrl: String
)