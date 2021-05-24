package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetDiskRapidInfo(
    @SerialName("errno")
    val errno: Int,
    @SerialName("info")
    val info: NetDiskPreCreateFile,
    @SerialName("request_id")
    val requestId: RequestIdType
)