package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

/**
 * 文本翻译结果
 * @param from 源语种方向
 * @param to 目标语种方向
 * @param results 翻译结果, 翻译服务会根据结果换行断句
 */
@Serializable
public data class TranslateTextResult(
    @SerialName("from")
    val from: String,
    @SerialName("to")
    val to: String,
    @SerialName("trans_result")
    val results: List<TransResult> = emptyList()
) {


    /**
     * @param src 翻译原文
     * @param dst 译文
     * @param srcTTS 原文tts
     * @param dstTTS 译文tts
     * @param dict 词典数据详情, json [doc](https://fanyiapp.cdn.bcebos.com/api/doc/百度翻译API词典资源-接入字段说明.pdf)
     */
    @Serializable
    public data class TransResult(
        @SerialName("dst")
        val dst: String,
        @SerialName("src")
        val src: String,
        @SerialName("src_tts")
        val srcTTS: String? = null,
        @SerialName("dst_tts")
        val dstTTS: String? = null,
        @SerialName("dict")
        val dict: String? = null
    )
}