package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

/**
 * 语音翻译请求结果
 * @param source 语音识别得到的原文
 * @param target 翻译后的目标语言文本
 * @param targetTTS 译文 TTS，使用base64编码
 */
@Serializable
public data class TranslateSpeechResult(
    @SerialName("source")
    val source: String,
    @SerialName("target")
    val target: String,
    @SerialName("target_tts")
    val targetTTS: String
)