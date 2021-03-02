package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.RequestIdType
import xyz.cssxsh.baidu.disk.item.NetDiskPreCreateFile

@Serializable
data class NetDiskRapidInfo(
    @SerialName("errno")
    val errno: Int,
    @SerialName("info")
    val info: NetDiskPreCreateFile,
    @SerialName("request_id")
    val requestId: RequestIdType
)