package xyz.cssxsh.baidu.disk

import kotlinx.serialization.*

@Serializable
data class NetDiskRapidInfo(
    @SerialName("errno")
    val errno: Int,
    @SerialName("info")
    val info: NetDiskPreCreateFile? = null,
    @SerialName("request_id")
    val requestId: RequestIdType
)