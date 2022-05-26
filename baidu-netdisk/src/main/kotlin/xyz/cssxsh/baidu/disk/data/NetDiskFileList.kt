package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*

@Serializable
public data class NetDiskFileList(
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
    val requestId: RequestIdType,
    @SerialName("list")
    val list: List<NetDiskFile>
)