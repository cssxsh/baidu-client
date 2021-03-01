package xyz.cssxsh.baidu

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import xyz.cssxsh.baidu.disk.*
import java.io.File
import java.util.*

internal class BaiduNetDiskClientTest {

    private val local = Properties().apply {
        File("../local.properties").inputStream().use {
            load(it)
        }
    }

    private val client = BaiduNetDiskClient(
        appName = local.getProperty("APP_NAME"),
        appId = local.getProperty("APP_ID").toLong(),
        appKey = local.getProperty("APP_KEY"),
        secretKey = local.getProperty("SECRET_KEY")
    ).apply { setToken(local.getProperty("ACCESS_TOKEN")) }

    private val dir = File(local.getProperty("TEST_DIR"))

    private val file = dir.resolve(local.getProperty("TEST_FILE"))

    private fun <T> T.withPrintln(): T = also { println(it) }

    @Test
    fun getUserInfo(): Unit = runBlocking {
        client.getUserInfo().withPrintln()
    }

    @Test
    fun getQuotaInfo(): Unit = runBlocking {
        client.getQuotaInfo().withPrintln()
    }

    @Test
    fun createDir(): Unit = runBlocking {
        client.createDir("/test").withPrintln()
    }

    @Test
    fun uploadFile(): Unit = runBlocking {
        client.uploadFile(file).withPrintln()
    }

    @Test
    fun loadFile(): Unit = runBlocking {
        val size = file.length().withPrintln()
        val content = file.digestContentMd5().withPrintln()
        val slice = file.digestSliceMd5().withPrintln()
        client.loadFile(size = size, content = content, slice = slice, path = "/temp_").withPrintln()
    }

    @Test
    fun listFile(): Unit = runBlocking {
        client.listFile().withPrintln()
        client.listAllFile(path = client.appDataFolder).withPrintln()
        client.listDoc().withPrintln()
        client.listBt().withPrintln()
        client.listVideo().withPrintln()
        client.getCategoryInfo().withPrintln()
        client.listCategoryFile(categories = listOf(CategoryType.AUDIO)).withPrintln()
        client.searchFile(key = "apk").withPrintln()
    }
}