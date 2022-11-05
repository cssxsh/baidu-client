package xyz.cssxsh.baidu.disk

import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import xyz.cssxsh.baidu.disk.data.*
import java.util.*

internal class BaiduPersonalCloudStorageTest : BaiduNetDiskClientTest() {

    @Test
    fun quota(): Unit = runBlocking {
        println(client.pcs.quota())
    }

    @Test
    fun file(): Unit = runBlocking {
        val single = client.pcs.upload(path = "UUID.txt", ondup = OnDupType.NEW_COPY, size = 37) {
            appendLine(UUID.randomUUID().toString())
        }

        val upload = RapidUploadInfo.parse(code = "${single.md5}#${single.md5}#${single.size}#${single.filename}")
        client.pcs.rapid(upload = upload, ondup = OnDupType.NEW_COPY)

        val path = "random.bin"
        val random = Random(System.currentTimeMillis())
        val buffer = ByteArray(4 shl 20)
        val blocks = arrayListOf<String>()
        repeat(3) {
            random.nextBytes(buffer)
            val temp = client.pcs.temp(path = path, size = buffer.size) {
                writeFully(buffer)
            }
            println(temp)
            blocks.add(temp.md5)
        }
        client.pcs.create(path = path, blocks = blocks, ondup = OnDupType.NEW_COPY)

//        val flow = channelFlow {
//            client.pcs.download(path = path) {
//
//                onDownload { bytesSentTotal, contentLength ->
//                    send(bytesSentTotal to contentLength)
//                }
//            }.body<ByteArray>()
//            close()
//        }
//        @OptIn(FlowPreview::class)
//        flow.sample(1_000).collect { (total, length) ->
//            println("${total}/${length}")
//        }

//        client.pcs.meta(path = "/")
        val meta = client.pcs.meta(paths = arrayOf(path))

//        client.pcs.list(path = "/", order = OrderType.TIME, desc = true, limit = 0..10)
//        client.pcs.list(path = "/", order = OrderType.TIME, desc = true, limit = 10..20)

        println(client.pcs.search(path = "", word = "random", recursion = true))

        client.pcs.mkdir(path = "temp")

        client.pcs.copy(path to "copy.bin")

        client.pcs.move(from = "copy.bin", to = "temp/move.bin")

        client.pcs.delete(path = path)

        client.pcs.recycle(start = 0, limit = 1000)
        client.pcs.restore(id = meta.list.single().id)

        val list = client.pcs.list(path = "", order = OrderType.TIME, desc = true, limit = null)
        client.pcs.delete(paths = list.list.map { it.path }.toTypedArray())
        client.pcs.clear()
    }
}