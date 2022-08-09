package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

@Serializable
public enum class TranslateStatus {
    NotStarted, Running, Succeeded, Failed
}