package xyz.cssxsh.baidu.aip.tts

import xyz.cssxsh.baidu.aip.AipExceptionInfo

/**
 * 语音生成任务异常
 */
public class SpeechTaskException(public val info: AipExceptionInfo) : IllegalStateException() {
    override val message: String get() = "${info.errorCode} - ${info.errorMessage}"
}