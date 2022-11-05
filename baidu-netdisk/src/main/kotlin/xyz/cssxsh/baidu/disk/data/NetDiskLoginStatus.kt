package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 网盘登录状态
 * @param login 登录信息
 */
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

    /**
     * 网盘登录信息
     * @param token 凭证
     * @param photo
     * @param id 用户ID
     * @param uk 用户内部ID
     * @param username 用户名
     * @param vipType VIP类型
     */
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