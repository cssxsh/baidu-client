package xyz.cssxsh.baidu

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import xyz.cssxsh.baidu.disk.*
import xyz.cssxsh.baidu.exception.*
import xyz.cssxsh.baidu.oauth.*
import java.io.*
import java.time.OffsetDateTime
import java.util.*

internal class BaiduNetDiskClientTest {

    private val local = Properties().apply {
        File("../local.properties").inputStream().use {
            load(it)
        }
    }

    private val client by lazy {
        runBlocking {
            BaiduNetDiskClient(
                appName = local.getProperty("APP_NAME"),
                appId = local.getProperty("APP_ID").toLong(),
                appKey = local.getProperty("APP_KEY"),
                secretKey = local.getProperty("SECRET_KEY")
            ).apply {
                setToken(local.getProperty("ACCESS_TOKEN"))
            }
        }
    }

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
    fun getListInfo(): Unit = runBlocking {
        client.getListInfo().withPrintln()

        client.listFile().withPrintln()
        client.listAllFile(path = client.appDataFolder).withPrintln()
        client.listDoc().withPrintln()
        client.listBt().withPrintln()
        client.listVideo().withPrintln()
        client.getCategoryInfo().withPrintln()
        client.listCategoryFile(categories = listOf(CategoryType.AUDIO)).withPrintln()
        client.searchFile(key = "apk").withPrintln()
    }

    @Test
    fun createFileWeb(): Unit = runBlocking {
        client.createFileWeb(size = 0, isDir = true, path = "/test", uploadId = null).withPrintln()
    }

    @Test
    fun createDir(): Unit = runBlocking {
        client.createDir(path = "/test").withPrintln()
    }

    @Test
    fun uploadFile(): Unit = runBlocking {
        client.uploadFile(file).withPrintln()
    }

    @Test
    fun uploadSingleFile(): Unit = runBlocking {
        client.uploadSingleFile(path = file.name, bytes = file.readBytes()).withPrintln()
    }

    @Test
    fun getMetaInfo(): Unit = runBlocking {
        client.getMetaInfo(path = "/apps/PixivHelper").withPrintln()
    }

    @Test
    fun rapidUploadFile(): Unit = runBlocking {
        client.rapidUploadFile(info = RapidUploadInfo.parse(code = local.getProperty("TEST_CODE"))).withPrintln()
    }

    @Test
    fun getFlashCode(): Unit = runBlocking {
        RapidUploadInfo.calculate(file).withPrintln()
        client.getRapidUploadInfo(path = file.name).withPrintln()
    }
}