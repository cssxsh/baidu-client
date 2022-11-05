package xyz.cssxsh.baidu.aip

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import xyz.cssxsh.baidu.aip.censor.*

/**
 * 内容审核
 * [ai-doc](https://ai.baidu.com/ai-doc/ANTIPORN/Jk3h6x8t2)
 */
public open class AipContentCensor(override val client: AipClient) : AipApplication {
    public companion object {
        internal const val IMAGE_CENSOR = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined"
        internal const val TEXT_CENSOR = "https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined"
        internal const val VIDEO_CENSOR = "https://aip.baidubce.com/rest/2.0/solution/v1/video_censor/v2/user_defined"
        internal const val VOICE_CENSOR = "https://aip.baidubce.com/rest/2.0/solution/v1/voice_censor/v2/user_defined"
    }

    /**
     * [内容审核平台-图像](https://ai.baidu.com/ai-doc/ANTIPORN/jk42xep4e)
     * @param url 图像URL地址
     * @param gif GIF动态图片
     */
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

    /**
     * [内容审核平台-图像](https://ai.baidu.com/ai-doc/ANTIPORN/jk42xep4e)
     * @param bytes 图像文件数据
     * @param gif GIF动态图片
     */
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

    /**
     * [内容审核平台-文本](https://ai.baidu.com/ai-doc/ANTIPORN/Rk3h6xb3i)
     * @param plain 待审核文本字符串
     */
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

    /**
     * [内容审核平台-短视频](https://ai.baidu.com/ai-doc/ANTIPORN/ak8iav4m6)
     * @param name 视频名称
     * @param url 视频主URL地址
     * @param id 视频在用户平台的唯一ID
     */
    public suspend fun video(name: String, url: String, id: String): CensorResult.Video {
        return video(name, listOf(url), VideoExtension(id = id))
    }

    /**
     * [内容审核平台-短视频](https://ai.baidu.com/ai-doc/ANTIPORN/ak8iav4m6)
     * @param name 视频名称
     * @param urls 视频主URL地址及备用URL地址
     * @param extension 视频信息
     */
    public suspend fun video(name: String, urls: List<String>, extension: VideoExtension): CensorResult.Video {
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
                append("extId", extension.id)
                append("extInfo", extension.info.toString())
            }) {
                url(VIDEO_CENSOR)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * [内容审核平台-短音频同步审核](https://ai.baidu.com/ai-doc/ANTIPORN/hk928u7bz)
     * @param url 音频文件的url地址
     * @param format 音频文件的格式，pcm、wav、amr、m4a，推荐pcm格式
     * @param extension 音频信息
     */
    public suspend fun voice(url: String, format: String, extension: VoiceExtension): CensorResult.Voice {
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                append("url", url)
                append("fmt", format)
                append("rate", extension.rate.toString())
                append("rawText", extension.rawText.toString())
                append("split", extension.split.toString())
                if (extension.account != null) append("account", extension.account)
                if (extension.audioId != null) append("audioId", extension.audioId)
            }) {
                url(VOICE_CENSOR)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }

    /**
     * [内容审核平台-短音频同步审核](https://ai.baidu.com/ai-doc/ANTIPORN/hk928u7bz)
     * @param bytes 音频文件数据
     * @param format 音频文件的格式，pcm、wav、amr、m4a，推荐pcm格式
     * @param extension 音频信息
     */
    public suspend fun voice(bytes: ByteArray, format: String, extension: VoiceExtension): CensorResult.Voice {
        val base64 = bytes.encodeBase64()
        return client.useHttpClient { http ->
            http.submitForm(Parameters.build {
                append("base64", base64)
                append("fmt", format)
                append("rate", extension.rate.toString())
                append("rawText", extension.rawText.toString())
                append("split", extension.split.toString())
                if (extension.account != null) append("account", extension.account)
                if (extension.audioId != null) append("audioId", extension.audioId)
            }) {
                url(VOICE_CENSOR)
                parameter("access_token", client.accessToken())
            }.body()
        }
    }
}