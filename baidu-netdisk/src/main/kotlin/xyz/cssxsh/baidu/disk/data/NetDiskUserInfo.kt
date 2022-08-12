package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 网盘用户信息
 * @param avatar 头像链接
 * @param baiduName 百度昵称
 * @param netdiskName 网盘昵称
 * @param uk 唯一ID
 * @param vip 会员类型
 */
@Serializable
public data class NetDiskUserInfo(
    @SerialName("avatar_url")
    val avatar: String,
    @SerialName("baidu_name")
    val baiduName: String,
    @SerialName("netdisk_name")
    val netdiskName: String,
    @SerialName("uk")
    val uk: Long,
    @SerialName("vip_type")
    val vip: VipType,
    @SerialName("errmsg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo