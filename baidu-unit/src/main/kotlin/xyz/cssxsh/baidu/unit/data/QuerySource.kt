package xyz.cssxsh.baidu.unit.data

import kotlinx.serialization.*

/**
 * 请求信息来源
 * @property ASR ASR为语音输入
 * @property KEYBOARD KEYBOARD为键盘文本输入
 */
@Serializable
public enum class QuerySource {
    ASR, KEYBOARD
}