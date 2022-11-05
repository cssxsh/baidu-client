package xyz.cssxsh.baidu.aip

import kotlinx.coroutines.*
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class BaiduApiClientTest {

    protected abstract val client: AipClient

    @BeforeAll
    fun init(): Unit = runBlocking {
        client.token()
    }
}