package xyz.cssxsh.baidu.aip

import kotlinx.coroutines.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.jupiter.api.*
import xyz.cssxsh.baidu.aip.tts.*
import xyz.cssxsh.baidu.oauth.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AipTextToSpeechTest : BaiduApiClientTest() {
    override val client: AipClient = BaiduAipClient(config = object : BaiduAuthConfig {
        override val appName: String = System.getenv("BAIDU_TTS_APP_NAME")
        override val appId: Long = System.getenv("BAIDU_TTS_APP_ID").toLong()
        override val appKey: String = System.getenv("BAIDU_TTS_APP_KEY")
        override val secretKey: String = System.getenv("BAIDU_TTS_SECRET_KEY")
    })

    private val tts = AipTextToSpeech(client = client)

    private val demo: List<List<DemoExample>> by lazy {
        this::class.java.getResourceAsStream("demo.json")!!.use {
            Json.decodeFromStream(it)
        }
    }

    @Serializable
    data class DemoExample(
        @SerialName("defaultText")
        val defaultText: String,
        @SerialName("name")
        val name: String,
        @SerialName("person")
        val person: Int,
        @SerialName("profile")
        val profile: String
    )

    @Test
    fun speech(): Unit = runBlocking {
        for (group in demo) {
            for (example in group) {
                try {
                    tts.handle(text = example.defaultText) {
                        person = example.person
                    }
                } catch (exception: SpeechException) {
                    println("${example.name} 不支持， ${exception.message}")
                    continue
                }
            }
        }
    }
}