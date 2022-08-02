package xyz.cssxsh.baidu.aip.nlp

import kotlinx.serialization.*
import xyz.cssxsh.baidu.aip.*

@Serializable
public data class SimilarityResult(
    @SerialName("log_id")
    override val logId: Long = 0,
    @SerialName("error_code")
    override val errorCode: Long? = null,
    @SerialName("error_msg")
    override val errorMessage: String? = null,
    @SerialName("score")
    val score: Double = 0.0,
    @SerialName("words")
    val words: Words = Words("", "")
) : AipExceptionInfo {
    @Serializable
    public data class Words(
        @SerialName("word_1")
        val first: String,
        @SerialName("word_2")
        val second: String
    )
}