package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

/**
 * 语音翻译请求数据
 */
@PublishedApi
@Serializable
internal data class TranslateSpeechRequest(
    @SerialName("from")
    val from: String,
    @SerialName("to")
    val to: String,
    @SerialName("voice")
    val voice: String,
    @SerialName("format")
    val format: String
)