package xyz.cssxsh.baidu.aip.translate

import kotlinx.serialization.*

/**
 * 翻译状态
 * @property NotStarted 待翻译
 * @property Running 翻译中
 * @property Succeeded 翻译成功
 * @property Failed 翻译失败
 */
@Serializable
public enum class TranslateStatus {
    NotStarted, Running, Succeeded, Failed
}