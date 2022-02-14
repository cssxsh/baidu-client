package xyz.cssxsh.baidu.aip.nlp

import kotlinx.serialization.*
import xyz.cssxsh.baidu.aip.*

@Serializable
public data class DNNResult(
    @SerialName("log_id")
    override val logId: Long = 0,
    @SerialName("error_code")
    override val errorCode: Long? = null,
    @SerialName("error_msg")
    override val errorMessage: String? = null,
    @SerialName("items")
    val items: List<Item> = emptyList(),
    @SerialName("ppl")
    val ppl: Double = 0.0,
    @SerialName("text")
    val text: String = ""
): AipExceptionInfo {
    @Serializable
    public data class Item(
        @SerialName("prob")
        val prob: Double,
        @SerialName("word")
        val word: String
    )
}