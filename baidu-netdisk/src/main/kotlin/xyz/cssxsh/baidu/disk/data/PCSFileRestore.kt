package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 文件恢复结果
 * @param id 文件ID
 * @param path 文件路径
 */
@Serializable
public data class PCSFileRestore(
    @SerialName("fs_id")
    val id: Long,
    @SerialName("path")
    val path: String
)