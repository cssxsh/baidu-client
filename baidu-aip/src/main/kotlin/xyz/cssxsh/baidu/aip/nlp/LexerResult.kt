package xyz.cssxsh.baidu.aip.nlp

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import xyz.cssxsh.baidu.aip.*

@Serializable
public data class LexerResult(
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
        @SerialName("basic_words")
        val basicWords: List<String>,
        @SerialName("byte_length")
        val byteLength: Int,
        @SerialName("byte_offset")
        val byteOffset: Int,
        @SerialName("formal")
        val formal: String,
        @SerialName("item")
        val item: String,
        @SerialName("loc_details")
        val locDetails: List<JsonObject>,
        @SerialName("ne")
        val ne: String,
        @SerialName("pos")
        val pos: String,
        @SerialName("uri")
        val uri: String
    )
}