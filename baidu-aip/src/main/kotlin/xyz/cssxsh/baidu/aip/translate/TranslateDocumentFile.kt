package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

/**
 * @param format 文件类型
 * @param filename 文件名称
 * @param size 文件大小
 * @param characterCount 文件字符数
 * @param url 文件下载地址
 */
@Serializable
public data class TranslateDocumentFile(
    @SerialName("character_count")
    val characterCount: Int = 0,
    @SerialName("filename")
    val filename: String,
    @SerialName("format")
    val format: String,
    @SerialName("size")
    val size: Int,
    @SerialName("url")
    val url: String = ""
)