package xyz.cssxsh.baidu.aip

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import xyz.cssxsh.baidu.aip.censor.*

/**
 * [ai-doc](https://ai.baidu.com/ai-doc/ANTIPORN/Jk3h6x8t2)
 */
public open class AipContentCensor(override val client: AipClient) : AipApplication {
    public companion object {
        internal const val IMAGE_CENSOR = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined"
        internal const val TEXT_CENSOR = "https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined"
        internal const val VIDEO_CENSOR = "https://aip.baidubce.com/rest/2.0/solution/v1/video_censor/v2/user_defined"
        internal const val VOICE_CENSOR = "https://aip.baidubce.com/rest/2.0/solution/v1/voice_censor/v2/user_defined"
    }

    public suspend fun image(url: String, gif: Boolean = false): CensorResult.Image {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                append("imgUrl", url)
                append("imgType", if (gif) "1" else "0")
            }) {
                url(IMAGE_CENSOR)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    public suspend fun image(bytes: ByteArray, gif: Boolean = false): CensorResult.Image {
        val base64 = bytes.encodeBase64()
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                append("image", base64)
                append("imgType", if (gif) "1" else "0")
            }) {
                url(IMAGE_CENSOR)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    public suspend fun text(plain: String): CensorResult.Text {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                append("text", plain)
            }) {
                url(TEXT_CENSOR)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    public suspend fun video(name: String, url: String): CensorResult.Video {
        return video(name, listOf(url), null)
    }

    public suspend fun video(name: String, urls: List<String>, extension: VideoExtension? = null): CensorResult.Video {
        check(urls.isNotEmpty()) { "Video urls is not empty." }
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                append("name", name)
                for ((index, item) in urls.withIndex()) {
                    if (index == 0) {
                        append("videoUrl", item)
                    } else {
                        append("videoUrl${index + 1}", item)
                    }
                }
                append("extId", extension?.id ?: urls.first())
                append("extInfo", extension?.info.toString())
            }) {
                url(VIDEO_CENSOR)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    public suspend fun voice(url: String, format: String, rawText: Boolean, split: Boolean): CensorResult.Voice {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                append("url", url)
                append("fmt", format)
                append("rawText", "$rawText")
                append("split", "$split")
            }) {
                url(VOICE_CENSOR)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    public suspend fun voice(bytes: ByteArray, format: String, rawText: Boolean, split: Boolean): CensorResult.Voice {
        val base64 = bytes.encodeBase64()
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                append("base64", base64)
                append("fmt", format)
                append("rawText", "$rawText")
                append("split", "$split")
            }) {
                url(VOICE_CENSOR)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }
}