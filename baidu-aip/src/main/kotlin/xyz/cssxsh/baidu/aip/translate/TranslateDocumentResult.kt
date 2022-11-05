package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

/**
 * 文档翻译结果
 * @param data 结果数据
 */
@Serializable
public data class TranslateDocumentResult(
    @SerialName("data")
    val `data`: TranslateDocumentData
)