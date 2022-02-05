package xyz.cssxsh.baidu.aip.tts

import io.ktor.client.features.*
import io.ktor.client.statement.*

public class SpeechException(response: HttpResponse, public val error: SpeechErrorInfo) :
    ResponseException(response, "") {
    override val message: String get() = error.message
}