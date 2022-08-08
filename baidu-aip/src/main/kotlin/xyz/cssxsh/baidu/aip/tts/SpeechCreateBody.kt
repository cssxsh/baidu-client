package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*

@Serializable
public data class SpeechCreateBody(
    @SerialName("format")
    val format: String,
    @SerialName("lang")
    val lang: String,
    @SerialName("pitch")
    val pitch: Int,
    @SerialName("speed")
    val speed: Int,
    @SerialName("text")
    val text: List<String>,
    @SerialName("voice")
    val voice: Int,
    @SerialName("volume")
    val volume: Int
)