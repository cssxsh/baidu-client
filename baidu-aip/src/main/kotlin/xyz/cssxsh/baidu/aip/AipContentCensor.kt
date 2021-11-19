package xyz.cssxsh.baidu.aip

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.json.*
import xyz.cssxsh.baidu.*

open class AipContentCensor(override val client: BaiduApiClient) : AipApplication {
    companion object {
        internal const val IMAGE_CENSOR = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined"
        internal const val TEXT_CENSOR = "https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined"
        internal const val VIDEO_CENSOR = "https://aip.baidubce.com/rest/2.0/solution/v1/video_censor/v2/user_defined"
        internal const val VOICE_CENSOR = "https://aip.baidubce.com/rest/2.0/solution/v1/voice_censor/v2/user_defined"
    }

    suspend fun image(url: String, gif: Boolean = false): JsonObject {
        return client.useHttpClient { http ->
            http.post(IMAGE_CENSOR) {
                parameter("access_token", accessToken)

                body = FormDataContent(Parameters.build {
                    append("imgUrl", url)
                    append("imgType", if (gif) "1" else "0")
                })
            }
        }
    }

    suspend fun image(bytes: ByteArray, gif: Boolean = false): JsonObject {
        @OptIn(InternalAPI::class)
        val base64 = bytes.encodeBase64()
        // TODO: check
        return client.useHttpClient { http ->
            http.post(IMAGE_CENSOR) {
                parameter("access_token", accessToken)

                body = FormDataContent(Parameters.build {
                    append("image", base64)
                    append("imgType", if (gif) "1" else "0")
                })
            }
        }
    }

    suspend fun text(plain: String): JsonObject {
        // TODO: check20000
        return client.useHttpClient { http ->
            http.post(TEXT_CENSOR) {
                parameter("access_token", accessToken)

                body = plain
                contentType(ContentType.Application.FormUrlEncoded)
            }
        }
    }

    suspend fun video(name: String, url: String, id: String? = null, urls: List<String>, info: JsonArray): JsonObject {
        // TODO: check
        return client.useHttpClient { http ->
            http.post(VIDEO_CENSOR) {
                parameter("access_token", accessToken)

                body = FormDataContent(Parameters.build {
                    append("name", name)
                    append("videoUrl", url)
                    append("extId", id ?: url)
                    for ((index, item) in urls.withIndex()) {
                        append("videoUrl${index + 2}", item)
                    }
                    append("extInfo", info.toString())
                })
            }
        }
    }

    suspend fun voice(url: String, format: String, rawText: Boolean, split: Boolean): JsonObject {
        // TODO: check20000
        return client.useHttpClient { http ->
            http.post(VOICE_CENSOR) {
                parameter("access_token", accessToken)

                body = FormDataContent(Parameters.build {
                    append("url", url)
                    append("fmt", format)
                    append("rawText", "$rawText")
                    append("split", "$split")
                })
            }
        }
    }

    suspend fun voice(bytes: ByteArray, format: String, rawText: Boolean, split: Boolean): JsonObject {
        @OptIn(InternalAPI::class)
        val base64 = bytes.encodeBase64()
        // TODO: check
        return client.useHttpClient { http ->
            http.post(VOICE_CENSOR) {
                parameter("access_token", accessToken)

                body = FormDataContent(Parameters.build {
                    append("base64", base64)
                    append("fmt", format)
                    append("rawText", "$rawText")
                    append("split", "$split")
                })
            }
        }
    }
}