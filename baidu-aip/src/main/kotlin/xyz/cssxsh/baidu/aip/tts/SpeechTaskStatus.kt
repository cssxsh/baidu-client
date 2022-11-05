package xyz.cssxsh.baidu.aip.tts

import kotlinx.serialization.*

/**
 * 任务状态
 * @property Created 已创建
 * @property Running 音频合成中
 * @property Success 音频合成成功
 * @property Failure 音频合成失败
 */
@Serializable
public enum class SpeechTaskStatus {
    Created, Running, Success, Failure
}