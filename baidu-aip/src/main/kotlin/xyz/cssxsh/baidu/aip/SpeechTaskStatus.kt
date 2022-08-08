package xyz.cssxsh.baidu.aip

import kotlinx.serialization.*

@Serializable
public enum class SpeechTaskStatus { Created, Running, Success, Failure }