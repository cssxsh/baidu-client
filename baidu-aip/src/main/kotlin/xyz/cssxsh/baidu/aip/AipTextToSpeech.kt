package xyz.cssxsh.baidu.aip

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.*
import xyz.cssxsh.baidu.aip.tts.*
import java.util.*
import kotlin.coroutines.*

/**
 * 文字转语音
 * [ai-doc](https://ai.baidu.com/ai-doc/SPEECH/yk38y8h3j)
 */
public open class AipTextToSpeech(override val client: AipClient) : AipApplication {
    public companion object {
        internal const val API = "https://tsn.baidu.com/text2audio"
        internal const val LONG_TTS_CREATE = "https://aip.baidubce.com/rpc/2.0/tts/v1/create"
        internal const val LONG_TTS_QUERY = "https://aip.baidubce.com/rpc/2.0/tts/v1/query"
    }

    public open val cuid: String = UUID.randomUUID().toString()

    public open val default: SpeechOption = SpeechOption()

    public open val duration: Long = 10_000

    public open val timeout: Long = 600_000

    /**
     * [短文本在线合成](https://ai.baidu.com/ai-doc/SPEECH/Qk38y8lrl)
     * @param text 合成的文本, 不超过60个汉字或者字母数字, 如需合成更长文本，推荐使用 长文本在线合成
     * @param option 合成参数
     * @see default
     */
    public suspend fun handle(text: String, option: SpeechOption = default.copy()): ByteArray {
        return client.useHttpClient { http ->
            http.prepareForm(API, Parameters.build {
                append("tex", text)
                append("tok", client.accessToken())
                append("cuid", cuid)
                append("ctp", "1")
                append("lan", "zh")
                append("spd", option.speed.toString())
                append("pit", option.pitch.toString())
                append("vol", option.volume.toString())
                append("per", option.person.toString())
                append("aue", option.format.toString())
            }).execute { response ->
                val type = requireNotNull(response.contentType()) { "ContentType is null." }
                when {
                    type.match(ContentType.Audio.Any) -> response.body()
                    type.match(ContentType.Application.Json) -> throw SpeechException(response, response.body())
                    else -> throw IllegalStateException("ContentType: $type not is audio.")
                }
            }
        }
    }

    /**
     * [短文本在线合成](https://ai.baidu.com/ai-doc/SPEECH/Qk38y8lrl)
     * @param text 合成的文本, 不超过60个汉字或者字母数字
     * @param block 调整合成参数
     * @see default
     */
    public suspend fun handle(text: String, block: SpeechOption.() -> Unit): ByteArray {
        return handle(text = text, option = default.copy().apply(block))
    }

    /**
     * [长文本在线合成](https://ai.baidu.com/ai-doc/SPEECH/gku5b9ejv)
     * @param paragraph 段落
     * @param option 合成参数
     * @see default
     */
    public suspend fun handle(vararg paragraph: String, option: SpeechOption = default.copy()): ByteArray {
        val task = task(
            request = SpeechCreateRequest(
                text = paragraph.asList(),
                voice = option.person,
                speed = option.speed,
                pitch = option.pitch,
                volume = option.volume,
                format = when (option.format) {
                    SpeechFormat.MP3 -> "mp3-48k"
                    SpeechFormat.WAV -> "wav"
                    SpeechFormat.PCM_8K -> "pcm-8k"
                    SpeechFormat.PCM_16K -> "pcm-16k"
                    else -> "mp3-16k"
                },
                lang = "zh"
            )
        )
        val id = task.taskId ?: throw SpeechTaskException(info = task)
        val limit = System.currentTimeMillis() + timeout

        while (coroutineContext.isActive) {
            val result = query(id)
            val tasks = result.tasks ?: throw SpeechTaskException(info = result)
            val info = tasks.find { it.id == id } ?: throw SpeechTaskException(info = result)
                .initCause(NoSuchElementException("Speech Task $id"))

            when (info.status) {
                SpeechTaskStatus.Created, SpeechTaskStatus.Running -> delay(duration)
                SpeechTaskStatus.Success -> {
                    val url = info.download?.speechUrl ?: throw SpeechTaskException(info = result)
                    val bytes = client.useHttpClient { http ->
                        http.prepareGet(url).body<ByteArray>()
                    }
                    return bytes
                }
                SpeechTaskStatus.Failure -> throw SpeechTaskException(info = result)
            }
            if (limit < System.currentTimeMillis()) throw IllegalStateException("Speech Task Timeout")
        }
        throw SpeechTaskException(info = task)
    }


    /**
     * [长文本在线合成](https://ai.baidu.com/ai-doc/SPEECH/gku5b9ejv)
     * @param paragraph 段落
     * @param block 调整合成参数
     * @see default
     * @see task
     * @see query
     */
    public suspend fun handle(vararg paragraph: String, block: SpeechOption.() -> Unit): ByteArray {
        return handle(paragraph = paragraph, option = default.copy().apply(block))
    }

    /**
     * [长文本在线合成 - 创建任务](https://ai.baidu.com/ai-doc/SPEECH/1ku59x8ey)
     * @param request 参数
     * @see query
     * @see handle
     */
    public suspend fun task(request: SpeechCreateRequest): SpeechTask {
        return client.useHttpClient { http ->
            http.preparePost(LONG_TTS_CREATE) {
                parameter("access_token", client.accessToken())

                contentType(ContentType.Application.Json)
                setBody(body = request)
            }.body()
        }
    }

    /**
     * [长文本在线合成 -查询任务结果](https://ai.baidu.com/ai-doc/SPEECH/Sku5ajfyo)
     * @param ids 任务id 推荐一次查询多个任务id，单次最多可查询200个
     * @see task
     * @see handle
     */
    public suspend fun query(vararg ids: String): SpeechTaskResult {
        return client.useHttpClient { http ->
            http.preparePost(LONG_TTS_QUERY) {
                parameter("access_token", client.accessToken())

                contentType(ContentType.Application.Json)
                setBody(body = SpeechQueryRequest(taskIds = ids.asList()))
            }.body()
        }
    }
}