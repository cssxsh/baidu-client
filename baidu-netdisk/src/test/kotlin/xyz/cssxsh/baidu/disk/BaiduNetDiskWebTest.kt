package xyz.cssxsh.baidu.disk

import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import xyz.cssxsh.baidu.disk.data.*
import kotlin.io.use

internal class BaiduNetDiskWebTest : BaiduNetDiskClientTest() {

    @Test
    fun quota(): Unit = runBlocking {
        println(client.web.quota())
//        println(client.web.status())
    }

    @Test
    fun list(): Unit = runBlocking {
        val file = generateRandomFile((4 shl 20) * 2 + System.currentTimeMillis() % (4 shl 20))

        println(client.web.categories(path = "", recursion = true))

        val option = NetDiskOption(
            path = "/",
            order = OrderType.TIME,
            desc = true,
            recursion = true
        )

        Assertions.assertTrue(client.web.list(page = 1, option = option).list.isNotEmpty())

        Assertions.assertTrue(client.web.category(type = CategoryType.VIDEO, page = 1, option = option).list.isNotEmpty())
        Assertions.assertTrue(client.web.category(type = CategoryType.AUDIO, page = 1, option = option).list.isNotEmpty())
        Assertions.assertTrue(client.web.category(type = CategoryType.IMAGE, page = 1, option = option).list.isNotEmpty())
        Assertions.assertTrue(client.web.category(type = CategoryType.DOCUMENT, page = 1, option = option).list.isNotEmpty())
        Assertions.assertTrue(client.web.category(type = CategoryType.APPLICATION, page = 1, option = option).list.isNotEmpty())
        Assertions.assertTrue(client.web.category(type = CategoryType.OTHERS, page = 1, option = option).list.isNotEmpty())
        Assertions.assertTrue(client.web.category(type = CategoryType.BITTORRENT, page = 1, option = option).list.isNotEmpty())

        Assertions.assertTrue(client.web.search(key = "7z", page = 1, option = option).list.isNotEmpty())

        val mkdir = client.web.mkdir(path = "temp")
        println(mkdir)

        val rapid = RapidUploadInfo.calculate(file = file).copy(path = "random.bin")
        println(rapid.format())

        val prepare = client.web.prepare(upload = rapid, blocks = LAZY_BLOCKS)
        println(prepare)
        Assertions.assertEquals(0, prepare.errorNo)
        val requestId = prepare.uploadId!!

        var index = 0
        val buffer = ByteArray(4 shl 20)
        val blocks = mutableListOf<String>()
        file.inputStream().use { input ->
            do {
                val size = input.read(buffer)
                if (size <= 0) {
                    break
                } else {
                    val temp = client.pcs.temp(path = rapid.path, id = requestId, index = index++, size = size) {
                        writeFully(buffer, 0, size)
                    }
                    blocks.add(temp.md5)
                }
            } while (true)
        }

        val merge = MergeFileInfo(
            blocks = blocks,
            uploadId = requestId,
            size = rapid.length,
            path = rapid.path
        )
        val create = client.web.create(merge = merge)
        println(create)
        Assertions.assertEquals(0, create.errorNo)


        val copy = client.web.copy(
            OperaFileInfo(path = create.path, dest = "temp", newname = "copy.bin"),
            ondup = OnDupType.NEW_COPY
        )
        println(copy)
        Assertions.assertEquals(0, copy.errorNo)

        if (copy.taskId != 0L) {
            val task = client.web.task(id = copy.taskId)
            println(task)
            Assertions.assertEquals(0, task.errorNo)
            delay(3_000)
        }

        val rename = client.web.rename(
            OperaFileInfo(path = "temp/copy.bin", newname = "rename.bin"),
            ondup = OnDupType.NEW_COPY
        )
        println(rename)
        Assertions.assertEquals(0, rename.errorNo)

        if (rename.taskId != 0L) {
            val task = client.web.task(id = rename.taskId)
            println(task)
            Assertions.assertEquals(0, task.errorNo)
            delay(3_000)
        }

        val move = client.web.move(
            OperaFileInfo(path = "random.bin", dest = "temp", newname = ""),
            ondup = OnDupType.NEW_COPY
        )
        println(move)
        Assertions.assertEquals(0, move.errorNo)

        if (move.taskId != 0L) {
            val task = client.web.task(id = move.taskId)
            println(task)
            Assertions.assertEquals(0, task.errorNo)
            delay(3_000)
        }

        val delete = client.web.delete(OperaFileInfo(path = "temp"))
        println(delete)
        Assertions.assertEquals(0, delete.errorNo)

        if (delete.taskId != 0L) {
            val task = client.web.task(id = delete.taskId)
            println(task)
            delay(3_000)
        }
    }

    @Test
    fun share(): Unit = runBlocking {
        val record = client.web.record(page = 1)
        Assertions.assertTrue(record.list.isNotEmpty())

        val surl = "7Pu7r3WfRmYc-38jyajyWw"
        val verify = client.rest.verify(surl = surl, password = "ltel")
        println(verify)
        Assertions.assertEquals(0, verify.errorNo)
        val view = client.web.view(surl = surl, key = verify.key)
        println(view)
        Assertions.assertEquals(0, view.errorNo)
    }

    @Test
    fun recycle(): Unit = runBlocking {
        client.web.recycle(page = 1)
        println(client.web.restore(0))
        println(client.web.clear())
    }
}