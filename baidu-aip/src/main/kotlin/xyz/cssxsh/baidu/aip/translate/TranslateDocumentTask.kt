package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

/**
 * 文档翻译任务
 * @param id 任务ID
 */
@Serializable
public data class TranslateDocumentTask(
    @SerialName("id")
    val id: String
)