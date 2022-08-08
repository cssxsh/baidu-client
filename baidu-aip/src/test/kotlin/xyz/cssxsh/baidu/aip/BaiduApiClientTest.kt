package xyz.cssxsh.baidu.aip

import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import java.io.*
import java.time.*
import java.util.*
import kotlin.properties.*
import kotlin.reflect.*

abstract class BaiduApiClientTest {

    protected abstract val client: AipClient

    @BeforeAll
    fun init(): Unit = runBlocking {
        client.token()
    }
}