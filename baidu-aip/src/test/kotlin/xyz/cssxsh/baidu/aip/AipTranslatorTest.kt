package xyz.cssxsh.baidu.aip

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import xyz.cssxsh.baidu.api.*
import xyz.cssxsh.baidu.aip.translate.*
import xyz.cssxsh.baidu.oauth.*
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AipTranslatorTest : BaiduApiClientTest() {
    override val client: AipClient = BaiduAipClient(config = object : BaiduAuthConfig {
        override val appName: String = System.getenv("BAIDU_MT_APP_NAME")
        override val appId: Long = System.getenv("BAIDU_MT_APP_ID").toLong()
        override val appKey: String = System.getenv("BAIDU_MT_APP_KEY")
        override val secretKey: String = System.getenv("BAIDU_MT_SECRET_KEY")
    })

    private val translator = AipTranslator(client = client)

    @Test
    fun text(): Unit = runBlocking {
        val result = translator.text(plain = "hello", to = "zh")
        Assertions.assertEquals(result.results.single().dst, "你好")
        Assertions.assertEquals(result.results.single().src, "hello")
    }

    @Test
    fun dict(): Unit = runBlocking {
        val result = translator.dict(plain = "hello", to = "zh").results.single()
        Assertions.assertEquals("你好", result.dst)
        Assertions.assertEquals("hello", result.src)
        Assertions.assertDoesNotThrow { result.dstTTS?.decodeBase64Bytes() }
        Assertions.assertDoesNotThrow { result.srcTTS?.decodeBase64Bytes() }
        Assertions.assertDoesNotThrow { result.dict?.let { BaiduJson.parseToJsonElement(it) } }
    }

    @Test
    fun picture(): Unit = runBlocking {
        val image = client.useHttpClient { http ->
            http.get("https://bce.bdstatic.com/doc/ai-cloud-share/MT/WechatIMG25_40acf71.jpeg")
                .body<ByteArray>()
        }

        val result = translator.picture(from = "zh", to = "en") { writeFully(image) }
        Assertions.assertEquals("这是一个测试 ", result.content[0].src)
        Assertions.assertEquals("这是一个例子 ", result.content[1].src)
        Assertions.assertEquals("This is a test", result.content[0].dst)
        Assertions.assertEquals("This is an example", result.content[1].dst)

        val paste1 = translator.picture(from = "zh", to = "en", paste = 1) { writeFully(image) }
        Assertions.assertDoesNotThrow { paste1.pasteImage!!.decodeBase64Bytes() }

        val paste2 = translator.picture(from = "zh", to = "en", paste = 2) { writeFully(image) }
        Assertions.assertDoesNotThrow { paste2.content[0].pasteImage!!.decodeBase64Bytes() }
        Assertions.assertDoesNotThrow { paste2.content[1].pasteImage!!.decodeBase64Bytes() }
    }

    /**
     * @see AipTextToSpeechTest.english
     */
    @Test
    fun speech(): Unit = runBlocking {
        val voice: ByteArray = File("./build/english.wav").readBytes()
        translator.speech(to = "en", from = "zh", voice = voice, format = "wav")
    }

    /**
     *
     */
    @Test
    fun document(): Unit = runBlocking {
        val pdf = client.useHttpClient { http ->
            http.get("https://bce-cdn.bj.bcebos.com/p3m/pdf/ai-cloud-share/online/MT/MT.pdf")
                .body<ByteArray>()
        }
        val input = TranslateDocumentRequest.Input(
            content = pdf.encodeBase64(),
            filename = "MT.pdf",
            format = "pdf"
        )
        val output = TranslateDocumentRequest.Output(
            filenamePrefix = "output",
            formats = listOf("pdf")
        )
        val data = translator.document(to = "zh", from = "en", input = input, output = output)

        Assertions.assertTrue(data.output.files.isNotEmpty())
        println(data)
    }
}