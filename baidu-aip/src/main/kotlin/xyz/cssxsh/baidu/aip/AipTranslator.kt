package xyz.cssxsh.baidu.aip

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import xyz.cssxsh.baidu.aip.translate.*
import kotlin.coroutines.*

/**
 * 翻译
 * [ai-doc](https://ai.baidu.com/ai-doc/MT/4kqryjku9)
 */
public open class AipTranslator(override val client: AipClient) : AipApplication {
    public companion object {
        internal const val TEXT_TRANSLATION = "https://aip.baidubce.com/rpc/2.0/mt/texttrans/v1"
        internal const val TEXT_TRANSLATION_WITH_DICT = "https://aip.baidubce.com/rpc/2.0/mt/texttrans-with-dict/v1"
        internal const val PICTURE_TRANSLATION = "https://aip.baidubce.com/file/2.0/mt/pictrans/v1"
        internal const val SPEECH__TRANSLATION = "https://aip.baidubce.com/rpc/2.0/mt/v2/speech-translation"
        internal const val DOC_TRANSLATION_CREATE = "https://aip.baidubce.com/rpc/2.0/mt/v2/doc-translation/create"
        internal const val DOC_TRANSLATION_QUERY = "https://aip.baidubce.com/rpc/2.0/mt/v2/doc-translation/query"
    }

    public open val duration: Long = 10_000

    public open val timeout: Long = 600_000

    /**
     * [文本翻译-通用版](https://ai.baidu.com/ai-doc/MT/4kqryjku9)
     * @param plain 原始文本
     * @param to 翻译目标语言
     * @param from 翻译源语言，设置 auto 时自动判断（null 会替换为 auto）
     * @param terms 术语库id 一次请求最多支持10个术语库，中间用英文逗号隔开；若相同的术语出现在多个术语库中，则排在前面的术语库生效优先级高
     */
    public suspend fun text(
        plain: String,
        to: String,
        from: String? = "auto",
        terms: List<String>? = null
    ): TranslateTextResult {
        return client.useHttpClient { http ->
            val responseBody = http.post(TEXT_TRANSLATION) {
                parameter("access_token", client.accessToken())

                contentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
                setBody(
                    body = TranslateTextRequest(
                        from = from ?: "auto",
                        query = plain,
                        to = to,
                        termIds = terms?.joinToString(",")
                    )
                )
            }.body<ResponseBody<TranslateTextResult>>()

            responseBody.result ?: throw BaiduAipException(info = responseBody)
        }
    }

    /**
     * [文本翻译-词典版](https://ai.baidu.com/ai-doc/MT/nkqrzmbpc)
     * @param plain 原始文本
     * @param to 翻译目标语言
     * @param from 翻译源语言，设置 auto 时自动判断（null 会替换为 auto）
     * @param terms 术语库id 一次请求最多支持10个术语库，中间用英文逗号隔开；若相同的术语出现在多个术语库中，则排在前面的术语库生效优先级高
     */
    public suspend fun dict(
        plain: String,
        to: String,
        from: String? = "auto",
        terms: List<String>? = null
    ): TranslateTextResult {
        return client.useHttpClient { http ->
            val responseBody = http.post(TEXT_TRANSLATION_WITH_DICT) {
                parameter("access_token", client.accessToken())

                contentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
                setBody(
                    body = TranslateTextRequest(
                        from = from ?: "auto",
                        query = plain,
                        to = to,
                        termIds = terms?.joinToString(",")
                    )
                )
            }.body<ResponseBody<TranslateTextResult>>()

            responseBody.result ?: throw BaiduAipException(info = responseBody)
        }
    }

    /**
     * [图片翻译](https://ai.baidu.com/ai-doc/MT/mki483xpu)
     * @param to 译文语种方向
     * @param from 源语种方向
     * @param paste 图片贴合类型：0 - 关闭文字贴合 1 - 返回整图贴合 2 - 返回块区贴合
     * @param image 传入翻译的图片
     */
    public suspend fun picture(
        to: String,
        from: String,
        paste: Int = 1,
        image: BytePacketBuilder.() -> Unit
    ): TranslatePictureResult {
        return client.useHttpClient { http ->
            val responseBody = http.submitFormWithBinaryData(PICTURE_TRANSLATION, formData {
                append("from".quote(), from)
                append("to".quote(), to)
                append("v".quote(), "3")
                append("paste".quote(), paste)
                append(key = "image".quote(), filename = "blob".quote(), bodyBuilder = image)
            }) {
                parameter("access_token", client.accessToken())
            }.body<ResponseBody<TranslatePictureResult>>()

            responseBody.result ?: throw BaiduAipException(info = responseBody)
        }
    }

    /**
     * [语音翻译](https://ai.baidu.com/ai-doc/MT/el4cmi76f)
     * @param to 目标语言
     * @param from 源语言
     * @param voice 音频文件二进制数据，需使用base64编码，编码后的大小不能超过4MB
     * @param format 音频格式：pcm、wav。并不是所有语言都支持全部的音频编码格式，支持情况请参考接口限制中的说明。
     */
    public suspend fun speech(
        to: String,
        from: String,
        voice: ByteArray,
        format: String
    ): TranslateSpeechResult {
        return client.useHttpClient { http ->
            val responseBody = http.post(SPEECH__TRANSLATION) {
                parameter("access_token", client.accessToken())

                contentType(ContentType.Application.Json)
                setBody(
                    body = TranslateSpeechRequest(
                        from = from,
                        to = to,
                        voice = voice.encodeBase64(),
                        format = format
                    )
                )
            }.body<ResponseBody<TranslateSpeechResult>>()

            responseBody.result ?: throw BaiduAipException(info = responseBody)
        }
    }

    /**
     * [文档翻译](https://ai.baidu.com/ai-doc/MT/Xky9x5xub)
     * @param to 目标语言
     * @param from 源语言
     * @param input 输入的文件内容
     * @param output 输出的文件内容
     * @see task
     * @see query
     */
    public suspend fun document(
        input: TranslateDocumentRequest.Input,
        output: TranslateDocumentRequest.Output,
        to: String,
        from: String? = "auto",
        domain: String? = "general",
    ): TranslateDocumentData {
        val task = task(input = input, output = output, to = to, from = from, domain = domain)

        while (coroutineContext.isActive) {
            val data = query(id = task.id).data
            val limit = System.currentTimeMillis() + timeout

            when (data.status) {
                TranslateStatus.NotStarted, TranslateStatus.Running -> delay(duration)
                TranslateStatus.Succeeded -> return data
                TranslateStatus.Failed -> throw IllegalStateException(data.reason)
            }
            if (limit < System.currentTimeMillis()) throw IllegalStateException("Speech Task Timeout")
        }
        throw IllegalStateException(task.id)
    }

    /**
     * [文档翻译 - 任务创建](https://ai.baidu.com/ai-doc/MT/Xky9x5xub)
     * @param to 目标语言
     * @param from 源语言
     * @param input 输入的文件内容
     * @param output 输出的文件内容
     * @see query
     * @see document
     */
    public suspend fun task(
        input: TranslateDocumentRequest.Input,
        output: TranslateDocumentRequest.Output,
        to: String,
        from: String? = "auto",
        domain: String? = "general",
    ): TranslateDocumentTask {
        return client.useHttpClient { http ->
            val responseBody = http.post(DOC_TRANSLATION_CREATE) {
                parameter("access_token", client.accessToken())

                contentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
                setBody(
                    body = TranslateDocumentRequest(
                        to = to,
                        from = from ?: "auto",
                        input = input,
                        output = output,
                        domain = domain ?: "general"
                    )
                )
            }.body<ResponseBody<TranslateDocumentTask>>()

            responseBody.result ?: throw BaiduAipException(info = responseBody)
        }
    }

    /**
     * [文档翻译 - 任务查询](https://ai.baidu.com/ai-doc/MT/Xky9x5xub)
     * @param id 任务id
     * @see task
     * @see document
     */
    public suspend fun query(
        id: String,
    ): TranslateDocumentResult {
        return client.useHttpClient { http ->
            val responseBody = http.post(DOC_TRANSLATION_QUERY) {
                parameter("access_token", client.accessToken())

                contentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
                setBody(
                    body = TranslateQueryRequest(id = id)
                )
            }.body<ResponseBody<TranslateDocumentResult>>()

            responseBody.result ?: throw BaiduAipException(info = responseBody)
        }
    }
}