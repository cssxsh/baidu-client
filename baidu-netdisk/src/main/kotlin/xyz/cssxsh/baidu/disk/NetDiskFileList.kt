package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.NumberToBooleanSerializer
import xyz.cssxsh.baidu.disk.item.NetDiskFile

@Serializable
data class NetDiskFileList(
    @SerialName("cursor")
    val cursor: Int,
    @SerialName("errmsg")
    val message: String,
    @SerialName("errno")
    val errno: Int,
    @SerialName("has_more")
    @Serializable(with = NumberToBooleanSerializer::class)
    val hasMore: Boolean = false,
    @SerialName("request_id")
    val requestId: String,
    @SerialName("list")
    val list: List<NetDiskFile>
)