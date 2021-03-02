package xyz.cssxsh.baidu.disk

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.RequestIdType
import xyz.cssxsh.baidu.disk.item.NetDiskPreCreateFile

@Serializable
data class NetDiskPreCreate(
    @SerialName("block_list")
    val blocks: List<Int> = emptyList(),
    @SerialName("errno")
    val errno: Int,
    @SerialName("path")
    val path: String? = null,
    @SerialName("request_id")
    val requestId: RequestIdType,
    @SerialName("return_type")
    val type: CreateReturnType,
    @SerialName("uploadid")
    val uploadId: String = "",
    @SerialName("info")
    val info: NetDiskPreCreateFile? = null,
)