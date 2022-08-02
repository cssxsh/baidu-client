package xyz.cssxsh.baidu.aip.nlp

import kotlinx.serialization.*
import xyz.cssxsh.baidu.aip.*

@Serializable
public data class DependencyResult(
    @SerialName("log_id")
    override val logId: Long = 0,
    @SerialName("error_code")
    override val errorCode: Long? = null,
    @SerialName("error_msg")
    override val errorMessage: String? = null,
    @SerialName("items")
    val items: List<Item> = emptyList(),
    @SerialName("text")
    val text: String = ""
) : AipExceptionInfo {
    @Serializable
    public data class Item(
        @SerialName("deprel")
        val deprel: String,
        @SerialName("head")
        val head: String,
        @SerialName("id")
        val id: String,
        @SerialName("postag")
        val postag: String,
        @SerialName("word")
        val word: String
    )
}