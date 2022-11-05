package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 文件修改结果
 * @param from 起始目录
 * @param to 目标目录
 */
@Serializable
public data class PCSFileChange(
    @SerialName("from")
    val from: String,
    @SerialName("to")
    val to: String
)