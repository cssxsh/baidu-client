package xyz.cssxsh.baidu

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import xyz.cssxsh.baidu.disk.*
import java.io.File

internal class BaiduNetDiskClientTest {

    private val token = "123.4414535ab6d23ad18544feb5ffb4f8ed.YaU_YJsvbAaHqciH78PvUeoU8tl9RaLhqYhYAd5.jW75BQ"

    private val client = BaiduNetDiskClient(
        appKey = "Ocyz2NgvSGcnZyRs7con1dQNeHPKzBd2",
        appName = "PixivHelper",
        appId = 23705658
    ).apply { setToken(token) }

    private val file = File("../test/pixez-flutter-0.3.8.apk")

    @Test
    fun getUserInfo(): Unit = runBlocking {
        client.getUserInfo().let {
            println(it)
        }
    }

    @Test
    fun getQuotaInfo(): Unit = runBlocking {
        client.getQuotaInfo().let {
            println(it)
        }
    }

    @Test
    fun createDir(): Unit = runBlocking {
        client.createDir("/test").let {
            println(it)
        }
    }

    @Test
    fun uploadFile(): Unit = runBlocking {
        client.uploadFile(file).let {
            println(it)
        }
    }

    @Test
    fun loadFile(): Unit = runBlocking {
        val size = file.length()
        val content = file.digestContentMd5() // 7afd260afub5fa182b0f60028960f103
        val slice = file.digestSliceMd5()
        println(size)
        println(content)
        println(slice)
        client.loadFile(size = size, content = content, slice = slice, path = "/temp_").let {
            println(it)
        }
    }
}