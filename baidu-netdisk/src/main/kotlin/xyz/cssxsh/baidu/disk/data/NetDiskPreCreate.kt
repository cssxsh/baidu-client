package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

@Serializable
public data class NetDiskPreCreate(
    @SerialName("block_list")
    val blocks: List<Int> = emptyList(),
    @SerialName("errno")
    val errno: Int,
    @SerialName("path")
    val path: String? = null,
    @SerialName("request_id")
    val requestId: RequestIdType,
    @SerialName("return_type")
    val type: CreateReturnType? = null,
    @SerialName("uploadid")
    val uploadId: String = "",
    @SerialName("info")
    val info: NetDiskPreCreateFile? = null,
)