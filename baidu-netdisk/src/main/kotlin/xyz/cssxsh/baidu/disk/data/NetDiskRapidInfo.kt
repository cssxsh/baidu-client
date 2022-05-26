package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

@Serializable
public data class NetDiskRapidInfo(
    @SerialName("errno")
    val errno: Int,
    @SerialName("info")
    val info: NetDiskPreCreateFile? = null,
    @SerialName("request_id")
    val requestId: RequestIdType
)