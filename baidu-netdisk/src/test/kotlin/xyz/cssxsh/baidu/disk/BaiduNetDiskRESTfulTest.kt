package xyz.cssxsh.baidu.disk

import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import xyz.cssxsh.baidu.disk.data.*
import java.time.*

internal class BaiduNetDiskRESTfulTest : BaiduNetDiskClientTest() {

    @Test
    fun user(): Unit = runBlocking {
        val user = client.rest.user()
        println(user.vip)
        Assertions.assertEquals(0, user.errorNo)
    }

    @Test
    fun search(): Unit = runBlocking {
        val search = client.rest.search(key = "7z", dir = "/", recursion = true, page = 1)
        Assertions.assertTrue(search.list.isNotEmpty())

        val query = client.rest.query(ids = search.list.map { it.id }, path = "/")
        Assertions.assertTrue(query.list.isNotEmpty())
        Assertions.assertTrue(query.list.all { it.dlink.isNotEmpty() })
    }

    @Test
    fun list(): Unit = runBlocking {
        val option = NetDiskOption(
            path = "/",
            order = OrderType.TIME,
            desc = true,
            recursion = true
        )

        val files = client.rest.list(start = 1, option = option)
        Assertions.assertTrue(files.list.isNotEmpty())
        val folders = client.rest.folder(start = 1, option = option)
        Assertions.assertTrue(folders.list.isNotEmpty())

        val all = client.rest.all(start = 0, option = option)
        Assertions.assertTrue(all.list.isNotEmpty())
        val empty = client.rest.all(start = 0, option = option.copy(created = OffsetDateTime.MAX)).list
        Assertions.assertTrue(empty.isEmpty())

        val others = client.rest.category(CategoryType.OTHERS, start = 0, option = option)
        Assertions.assertTrue(others.list.isNotEmpty())
        Assertions.assertTrue(others.list.all { it.category == CategoryType.OTHERS })

        val bin = client.rest.category(CategoryType.OTHERS, start = 0, option = option.copy(formats = listOf("bin")))
        Assertions.assertTrue(bin.list.isNotEmpty())
        Assertions.assertTrue(bin.list.all { it.filename.endsWith(".bin") })

        val application = client.rest.category(CategoryType.APPLICATION, start = 0, option = option)
        Assertions.assertTrue(application.list.isNotEmpty())
        Assertions.assertTrue(application.list.all { it.category == CategoryType.APPLICATION })

        val video = client.rest.video(page = 1, option = option)
        Assertions.assertTrue(video.list.isNotEmpty())
        Assertions.assertTrue(video.list.all { it.category == CategoryType.VIDEO })

        val audio = client.rest.audio(page = 1, option = option)
        Assertions.assertTrue(audio.list.isNotEmpty())
        Assertions.assertTrue(audio.list.all { it.category == CategoryType.AUDIO })

        val image = client.rest.image(page = 1, option = option)
        Assertions.assertTrue(image.list.isNotEmpty())
        Assertions.assertTrue(image.list.all { it.category == CategoryType.IMAGE })

        val document = client.rest.document(page = 1, option = option)
        Assertions.assertTrue(document.list.isNotEmpty())
        Assertions.assertTrue(document.list.all { it.category == CategoryType.DOCUMENT })

        val bittorrent = client.rest.bittorrent(page = 1, option = option)
        Assertions.assertTrue(bittorrent.list.isNotEmpty())
        Assertions.assertTrue(bittorrent.list.all { it.category == CategoryType.BITTORRENT })
    }

    @Test
    fun manager(): Unit = runBlocking {
        val file = generateRandomFile((4 shl 20) * 2 + System.currentTimeMillis() % (4 shl 20))

        val rapid = RapidUploadInfo.calculate(file = file)
        println(rapid.format())

        val host = client.host()
        println(host.server)
        Assertions.assertEquals(0, host.errorCode)

        val upload = client.upload(file = file, path = "random.bin", ondup = OnDupType.NEW_COPY)
        println(upload)
        Assertions.assertNotEquals(0, upload.id)

        val mkdir = client.rest.mkdir(path = "temp", ondup = OnDupType.SKIP)
        println(mkdir)
        Assertions.assertEquals(0, mkdir.errorNo)

        val mkdir2 = client.rest.mkdir(path = "temp", ondup = OnDupType.FAIL)
        println(mkdir2)
        Assertions.assertNotEquals(0, mkdir2.errorNo)

        val copy = client.rest.copy(
            OperaFileInfo(path = "random.bin", dest = "temp", newname = "copy.bin"),
            ondup = OnDupType.NEW_COPY
        )
        println(copy)
        Assertions.assertEquals(0, copy.errorNo)

        val rename = client.rest.rename(
            OperaFileInfo(path = "temp/copy.bin", newname = "rename.bin"),
            ondup = OnDupType.NEW_COPY
        )
        println(rename)
        Assertions.assertEquals(0, rename.errorNo)

        val move = client.rest.move(
            OperaFileInfo(path = "random.bin", dest = "temp", newname = ""),
            ondup = OnDupType.NEW_COPY
        )
        println(move)
        Assertions.assertEquals(0, move.errorNo)

        val delete = client.rest.delete(OperaFileInfo(path = "temp"))
        println(delete)
        Assertions.assertEquals(0, delete.errorNo)
    }

    @Test
    fun share(): Unit = runBlocking {
        // https://pan.baidu.com/share/init?surl={}
        // https://pan.baidu.com/s/1{}

//        val option = ShareOption(period = 0, channel = 4, easy = false)
//        val share = client.rest.share(password = "test", option = option, 925171613014809)
//        println(share)

        val record = client.rest.record(page = 1)
        Assertions.assertTrue(record.list.isNotEmpty())

        val surl = "7Pu7r3WfRmYc-38jyajyWw"
        val verify = client.rest.verify(surl = surl, password = "ltel")
        println(verify)
        Assertions.assertEquals(0, verify.errorNo)
        val view = client.rest.view(surl = surl, key = verify.key)
        println(view)
        Assertions.assertEquals(0, view.errorNo)

        val transfer = client.rest.transfer(
            info = TransferFileInfo(
                shareId = view.shareId,
                from = view.uk,
                key = verify.key,
                files = view.list.map { it.id }
            ),
            path = "",
            ondup = OnDupType.NEW_COPY
        )
        println(transfer)
        Assertions.assertEquals(0, transfer.errorNo)
    }

    @Disabled
    @Test
    fun device(): Unit = runBlocking {
//        println(client.rest.unbind(id = "104771607rs1607808"))
        // Text: "{"errmsg":"exceed max bind count per device","errno":42103,"last_user":"贾**咖啡","request_id":"8668515804744130820"}"
        println(client.rest.bind(id = "166066882735070540", name = "度小云"))
        println(client.rest.check(id = "166066882735070540"))
        println(client.rest.bind(id = "190877501167031219", name = "度小云"))
        println(client.rest.check(id = "190877501167031219"))
        println(client.rest.devices(categories = (0..10).toList().toIntArray()))
        println(client.rest.diff(categories = (0..10).toList().toIntArray(), cursor = ""))
        println(client.rest.unbind(id = "166066882735070540"))
        println(client.rest.unbind(id = "190877501167031219"))
    }
}