package xyz.cssxsh.baidu.aip

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import xyz.cssxsh.baidu.*
import xyz.cssxsh.baidu.aip.translate.*

/**
 * [ai-doc](https://ai.baidu.com/ai-doc/MT/4kqryjku9)
 */
public open class AipTranslator(override val client: AipClient) : AipApplication {
    public companion object {
        internal const val TEXT_TRANSLATION = "https://aip.baidubce.com/rpc/2.0/mt/texttrans/v1"
        internal const val TEXT_TRANSLATION_WITH_DICT = "https://aip.baidubce.com/rpc/2.0/mt/texttrans-with-dict/v1"
        internal const val PICTURE_TRANSLATION = "https://aip.baidubce.com/file/2.0/mt/pictrans/v1"
    }

    public suspend fun text(plain: String, to: String, from: String?, terms: List<String>? = null): TranslateResult.Text {
        return client.useHttpClient { http ->
            http.post(TEXT_TRANSLATION) {
                parameter("access_token", accessToken)

                body = buildJsonObject {
                    put("from", from ?: "auto")
                    put("to", to)
                    put("query", plain)
                    put("termIds", terms?.joinToString(separator = ","))
                }
                contentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
            }
        }
    }

    public suspend fun dict(plain: String, to: String, from: String?, terms: List<String>? = null): TranslateResult.Text {
        return client.useHttpClient { http ->
            http.post(TEXT_TRANSLATION_WITH_DICT) {
                parameter("access_token", accessToken)

                body = buildJsonObject {
                    put("from", from ?: "auto")
                    put("to", to)
                    put("query", plain)
                    put("termIds", terms?.joinToString(separator = ","))
                }
                contentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
            }
        }
    }

    public suspend fun picture(to: String, from: String, bytes: ByteArray): TranslateResult.Picture {
        return client.useHttpClient { http ->
            http.post(PICTURE_TRANSLATION) {
                parameter("access_token", accessToken)

                body = MultiPartFormDataContent(formData {
                    append(key = "to", value = to)
                    append(key = "from", value = from)
                    append(key = "v", value = 3)
                    append(key = "file", value = bytes)
                })
            }
        }
    }
}