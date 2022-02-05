package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*

@Serializable
public data class SpeechOption(
    /**
     * 语速 0~15
     */
    var speed: Int = 5,
    /**
     * 音调 0~15
     */
    var pitch: Int = 5,
    /**
     * 音量 0~15
     */
    var volume: Int = 5,
    /**
     * 讲述人，音库
     * @see SpeechPerson
     */
    var person: Int = SpeechPerson.Boutique.LovelyFemale,
    /**
     * 编码格式
     * @see SpeechFormat
     */
    var format: Int = SpeechFormat.MP3
)
