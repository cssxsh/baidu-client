package xyz.cssxsh.baidu.aip.tts

import io.ktor.client.plugins.*
import io.ktor.client.statement.*

/**
 * 语音 异常
 * @property error 错误信息
 */
public class SpeechException(response: HttpResponse, public val error: SpeechErrorInfo) :
    ResponseException(response, "") {
    override val message: String get() = error.errorMessage
}