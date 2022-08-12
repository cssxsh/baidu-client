package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

@Serializable
public data class PCSFileRestore(
    @SerialName("fs_id")
    val id: Long,
    @SerialName("path")
    val path: String
)