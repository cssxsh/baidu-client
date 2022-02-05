package xyz.cssxsh.baidu.aip

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import xyz.cssxsh.baidu.*
import xyz.cssxsh.baidu.aip.tts.*
import java.util.*

/**
 * [ai-doc](https://ai.baidu.com/ai-doc/SPEECH/Gk38y8lzk)
 */
public open class AipTextToSpeech(override val client: AipClient) : AipApplication {
    public companion object {
        internal const val API = "https://tsn.baidu.com/text2audio"
    }

    public open val cuid: String = UUID.randomUUID().toString()

    public open val default: SpeechOption = SpeechOption()

    public suspend fun handle(text: String, option: SpeechOption = default.copy()): ByteArray {
        return client.useHttpClient { http ->
            http.post<HttpStatement>(API) {
                body = FormDataContent(Parameters.build {
                    append("tex", text)
                    append("tok", accessToken.encodeURLQueryComponent(charset = charset("GBK")))
                    append("cuid", cuid)
                    append("ctp", "1")
                    append("lan", "zh")
                    append("spd", option.speed.toString())
                    append("pit", option.pitch.toString())
                    append("vol", option.volume.toString())
                    append("per", option.person.toString())
                    append("aue", option.format.toString())
                })
            }.execute { response ->
                val type = requireNotNull(response.contentType()) { "ContentType is null." }
                when {
                    type.match(ContentType.Audio.Any) -> response.receive()
                    type.match(ContentType.Application.Json) -> throw SpeechException(response, response.receive())
                    else -> throw IllegalStateException("ContentType: $type not is audio.")
                }
            }
        }
    }

    public suspend fun handle(text: String, block: SpeechOption.() -> Unit): ByteArray {
        return handle(text, default.copy().apply(block))
    }
}