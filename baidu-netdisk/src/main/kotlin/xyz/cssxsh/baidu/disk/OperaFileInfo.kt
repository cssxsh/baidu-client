package xyz.cssxsh.baidu.disk

import kotlinx.serialization.*


/**
 * 操作信息
 * @param path 被操作的文件
 * @param dest 目标文件夹（移动，复制）
 * @param newname 新文件名（移动，复制， 重命名）, 为空时文件名不变
 */
@Serializable
public data class OperaFileInfo(
    @SerialName("path")
    val path: String,
    @SerialName("dest")
    val dest: String = "",
    @SerialName("newname")
    val newname: String = ""
)