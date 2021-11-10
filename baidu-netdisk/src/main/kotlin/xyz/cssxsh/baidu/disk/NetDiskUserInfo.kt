package xyz.cssxsh.baidu.disk

import kotlinx.serialization.*

@Serializable
data class NetDiskUserInfo(
    @SerialName("avatar_url")
    val avatar: String,
    @SerialName("baidu_name")
    val baiduName: String,
    @SerialName("errmsg")
    val message: String,
    @SerialName("errno")
    val errno: Int,
    @SerialName("netdisk_name")
    val netdiskName: String,
    @SerialName("request_id")
    val requestId: RequestIdType,
    @SerialName("uk")
    val uk: Long,
    @SerialName("vip_type")
    val vip: VipType
)