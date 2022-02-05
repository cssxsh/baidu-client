package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*
import xyz.cssxsh.baidu.aip.*

public sealed class TranslateResult : AipExceptionInfo {
    public abstract val result: Any

    @Serializable
    public data class Text(
        @SerialName("log_id")
        override val logId: Long,
        @SerialName("result")
        override val result: Result = Result(),
        @SerialName("error_code")
        override val errorCode: Long? = null,
        @SerialName("error_msg")
        override val errorMessage: String? = null
    ) : TranslateResult() {

        @Serializable
        public data class Result(
            @SerialName("from")
            val from: String = "",
            @SerialName("to")
            val to: String = "",
            @SerialName("trans_result")
            val results: List<TransResult> = emptyList()
        )

        @Serializable
        public data class TransResult(
            @SerialName("dst")
            val dst: String,
            @SerialName("src")
            val src: String,
            @SerialName("dst_tts")
            val dstTTS: String? = null,
            @SerialName("src_tts")
            val srcTTS: String? = null,
            @SerialName("dict")
            val dict: String? = null,
        )
    }

    @Serializable
    public data class Picture(
        @SerialName("data")
        override val result: Data,
        @SerialName("error_code")
        override val errorCode: Long = 0,
        @SerialName("error_msg")
        override val errorMessage: String = ""
    ) : TranslateResult() {

        @Serializable
        public data class Data(
            @SerialName("content")
            val results: List<Content> = emptyList(),
            @SerialName("from")
            val from: String = "",
            @SerialName("pasteImg")
            val pasteImage: String = "",
            @SerialName("sumDst")
            val sumDst: String = "",
            @SerialName("sumSrc")
            val sumSrc: String = "",
            @SerialName("to")
            val to: String = ""
        )

        @Serializable
        public data class Content(
            @SerialName("dst")
            val dst: String,
            @SerialName("lineCount")
            val lineCount: Int,
            @SerialName("pasteImg")
            val pasteImage: String,
            @SerialName("points")
            val points: List<Point>,
            @SerialName("rect")
            val rect: String,
            @SerialName("src")
            val src: String
        )

        @Serializable
        public data class Point(
            @SerialName("x")
            val x: Int,
            @SerialName("y")
            val y: Int
        )
    }
}