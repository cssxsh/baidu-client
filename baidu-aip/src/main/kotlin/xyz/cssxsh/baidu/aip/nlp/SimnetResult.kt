package xyz.cssxsh.baidu.aip.nlp

import kotlinx.serialization.*
import xyz.cssxsh.baidu.aip.*

@Serializable
public data class SimnetResult(
    @SerialName("log_id")
    override val logId: Long = 0,
    @SerialName("error_code")
    override val errorCode: Long? = null,
    @SerialName("error_msg")
    override val errorMessage: String? = null,
    @SerialName("score")
    val score: Double = 0.0,
    @SerialName("texts")
    val texts: Texts = Texts("", "")
): AipExceptionInfo {
    @Serializable
    public data class Texts(
        @SerialName("text_1")
        val first: String,
        @SerialName("text_2")
        val second: String
    )
}