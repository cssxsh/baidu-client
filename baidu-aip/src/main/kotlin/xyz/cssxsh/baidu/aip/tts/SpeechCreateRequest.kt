package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*

/**
 * 创建任务请求
 * @param text 待合成的文本，需要为UTF-8编码；输入多段文本时，文本间会插入1s长度的空白间隔
 * @param format 音频格式 "mp3-16k"，"mp3-48k"，"wav"，"pcm-8k"，"pcm-16k"，默认为mp3-16k
 * @param voice 音库 [SpeechPerson]
 * @param lang 语言 固定值zh
 * @param speed 语速 取值0-15 默认为5
 * @param pitch 音调 取值0-15 默认为5
 * @param volume 音量 取值0-15 默认为5
 * @see SpeechPerson
 */
@Serializable
public data class SpeechCreateRequest(
    @SerialName("format")
    val format: String = "mp3-16k",
    @Required
    @SerialName("lang")
    val lang: String = "zh",
    @SerialName("pitch")
    val pitch: Int = 5,
    @SerialName("speed")
    val speed: Int = 5,
    @SerialName("text")
    val text: List<String>,
    @SerialName("voice")
    val voice: Int = SpeechPerson.Base.MatureFemale,
    @SerialName("volume")
    val volume: Int = 5
)