package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*

/**
 * @param speed 语速 取值0-15 默认为5
 * @param pitch 音调 取值0-15 默认为5
 * @param volume 音量 取值0-15 默认为5
 * @param volume 讲述人，音库 [SpeechPerson]
 * @param format 讲述人，音库 [SpeechFormat]
 * @see SpeechPerson
 * @see SpeechFormat
 */
@Serializable
public data class SpeechOption(
    var speed: Int = 5,
    var pitch: Int = 5,
    var volume: Int = 5,
    var person: Int = SpeechPerson.Boutique.LovelyFemale,
    var format: Int = SpeechFormat.MP3
)
