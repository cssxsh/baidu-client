package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*

@Serializable
public enum class SpeechTaskStatus { Created, Running, Success, Failure }