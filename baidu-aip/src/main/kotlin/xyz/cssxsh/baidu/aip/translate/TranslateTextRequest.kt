package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

/**
 * 文本翻译请求数据
 */
@Serializable
internal data class TranslateTextRequest(
    @SerialName("from")
    val from: String,
    @SerialName("q")
    val query: String,
    @SerialName("termIds")
    val termIds: String? = null,
    @SerialName("to")
    val to: String
)