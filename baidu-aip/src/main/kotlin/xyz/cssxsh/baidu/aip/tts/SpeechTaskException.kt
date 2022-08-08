package xyz.cssxsh.baidu.aip.tts

import xyz.cssxsh.baidu.aip.AipExceptionInfo

public class SpeechTaskException(public val info: AipExceptionInfo) : IllegalStateException() {
    override val message: String get() = "${info.errorCode} - ${info.errorMessage}"
}