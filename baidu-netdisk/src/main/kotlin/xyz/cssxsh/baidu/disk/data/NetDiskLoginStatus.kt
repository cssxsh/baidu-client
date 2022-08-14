package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

@Serializable
public data class NetDiskLoginStatus(
    @SerialName("login_info")
    val login: LoginInfo,
    @SerialName("show_msg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo {

    @Serializable
    public data class LoginInfo(
        @SerialName("bdstoken")
        val token: String,
        @SerialName("photo_url")
        val photo: String,
        @SerialName("uk")
        val uk: Int,
        @SerialName("uk_str")
        val id: String,
        @SerialName("username")
        val username: String,
        @SerialName("vip_identity")
        val vipIdentity: String,
        @SerialName("vip_level")
        val vipLevel: Int,
        @SerialName("vip_point")
        val vipPoint: Int,
        @SerialName("vip_type")
        val vipType: String
    )
}