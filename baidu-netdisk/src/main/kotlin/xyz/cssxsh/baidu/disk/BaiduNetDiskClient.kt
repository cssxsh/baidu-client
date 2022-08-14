package xyz.cssxsh.baidu.disk

import io.ktor.client.call.*
import io.ktor.client.content.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import xyz.cssxsh.baidu.disk.data.*
import xyz.cssxsh.baidu.oauth.*
import java.io.*
import java.security.*
import java.time.*

/**
 * 构建客户端的参数需要到 [百度网盘开放中心](https://pan.baidu.com/union/apply) 申请
 * @property pcs PersonalCloudStorage API
 * @property rest RESTful API
 * @property web Web API
 */
public open class BaiduNetDiskClient(override val config: BaiduNetDiskConfig) : AbstractNetDiskClient() {
    public constructor(config: BaiduNetDiskConfig, token: AuthorizeAccessToken) : this(config) {
        save(token = token)
    }

    public val pcs: BaiduPersonalCloudStorage by lazy { BaiduPersonalCloudStorage(client = this) }

    public val rest: BaiduNetDiskRESTful by lazy { BaiduNetDiskRESTful(client = this) }

    public val web: BaiduNetDiskWeb by lazy { BaiduNetDiskWeb(client = this) }

    /**
     * 创建一个目录
     * @param path 路径
     */
    public suspend fun mkdir(path: String): NetDiskFileInfo = rest.mkdir(path = path, ondup = OnDupType.SKIP)

    /**
     * 分段上传一个文件
     * @param file 本地文件
     * @param path 云端文件路径
     * @param ondup 文件冲突策略
     */
    public suspend fun upload(file: File, path: String, ondup: OnDupType = OnDupType.FAIL): NetDiskFileInfo {
        check(file.isFile) { "${file.absolutePath} 不是文件" }
        check(file.length() > 0) { "${file.absolutePath} 文件是空的" }

        val user = rest.user()
        val length = file.length()
        check(length <= user.vip.updateLimit) { "超过当前用户${user}上传上限" }

        val limit = user.vip.superLimit
        val blocks: MutableList<String> = ArrayList((length / limit).toInt() + 1)

        val content = MessageDigest.getInstance("md5")
        val block = MessageDigest.getInstance("md5")

        file.forEachBlock(limit) { buffer, size ->
            content.update(buffer, 0, size)

            block.update(buffer, 0, size)
            blocks.add(block.digest().toHexString())
        }
        val temp = ByteArray(minOf(SLICE_SIZE, length.toInt()))
        file.inputStream().use { input -> input.read(temp) }
        block.update(temp)

        val modified = OffsetDateTime.ofInstant(Instant.ofEpochSecond(file.lastModified()), ZoneId.systemDefault())
        val upload = RapidUploadInfo(
            content = content.digest().toHexString(),
            slice = block.digest().toHexString(),
            length = length,
            path = file.name,
            created = modified,
            modified = modified
        )
        val prepare = rest.prepare(upload = upload, blocks = blocks, ondup = ondup)
        if (prepare.type == PrepareReturnType.EXIST) return requireNotNull(prepare.file) { prepare.toString() }
        requireNotNull(prepare.uploadId) { prepare.toString() }

        val buffer = ByteArray(limit)
        file.inputStream().use { input ->
            (0 until length step limit.toLong()).mapIndexed { index, _ ->
                val size = runInterruptible(Dispatchers.IO) { input.read(buffer) }

                if (index in prepare.need) {
                    pcs.temp(path = path, id = prepare.uploadId, index = index, size = size) {
                        writeFully(buffer, 0, size)
                    }
                }
            }
        }

        return rest.create(
            merge = MergeFileInfo(
                blocks = blocks,
                uploadId = prepare.uploadId,
                path = path,
                size = length,
                created = modified,
                modified = modified
            ),
            ondup = ondup
        )
    }

    /**
     * 整体上传一个文件, 适合 4M 以下小文件
     * @param file 本地文件
     * @param path 云端文件路径
     * @param ondup 文件冲突策略
     */
    public suspend fun single(file: File, path: String, ondup: OnDupType = OnDupType.FAIL): NetDiskFileInfo {
        return pcs.upload(path = path, ondup = ondup, size = file.length()) {
            writeFully(file.readBytes())
        }
    }

    /**
     * 快速上传文件 需要Rapid信息
     * @param upload Rapid信息，可以通过秒传码获得, 也可以通过文件计算
     * @param ondup 文件冲突策略
     * @see RapidUploadInfo.parse
     * @see RapidUploadInfo.calculate
     */
    public suspend fun rapid(upload: RapidUploadInfo, ondup: OnDupType = OnDupType.FAIL): NetDiskFileInfo {
        return pcs.rapid(upload = upload, ondup = ondup)
    }

    /**
     * 获取网盘文件的 Rapid 信息，可以用于生成秒传码
     * @param path 文件路径
     * @see rapid
     * @see RapidUploadInfo.format
     * @param
     */
    public suspend fun flash(path: String): RapidUploadInfo {
        return pcs.download(path = path) {
            header(HttpHeaders.Range, "bytes=0-${SLICE_SIZE - 1}")
        }.execute { response ->
            val digest = MessageDigest.getInstance("md5")
            val bytes = response.body<ByteArray>()
            digest.update(bytes)

            RapidUploadInfo(
                content = requireNotNull(response.headers[HttpHeaders.ETag]) { "Not Found ETag" },
                slice = digest.digest().toHexString(),
                length = requireNotNull(response.headers[HttpHeaders.ContentRange]) { "Not Found ContentRange" }
                    .substringAfterLast('/')
                    .toLong(),
                path = path
            )
        }
    }

    /**
     * 复制文件, 如需要异步或批量操作 请直接使用 [rest] [web] [pcs] 的 api
     * @param path 源路径
     * @param dest 目的路径
     * @param newname 新文件名
     * @param ondup 遇到重复文件的处理策略
     * @see BaiduNetDiskRESTful.move
     */
    public suspend fun copy(path: String, dest: String, newname: String, ondup: OnDupType): NetDiskOpera {
        return rest.move(OperaFileInfo(path, dest, newname), ondup = ondup)
    }

    /**
     * 移动文件, 如需要异步或批量操作 请直接使用 [rest] [web] [pcs] 的 api
     * @param path 源路径
     * @param dest 目的路径
     * @param newname 新文件名
     * @param ondup 文件冲突策略
     * @see BaiduNetDiskRESTful.move
     */
    public suspend fun move(path: String, dest: String, newname: String, ondup: OnDupType): NetDiskOpera {
        return rest.move(OperaFileInfo(path, dest, newname), ondup = ondup)
    }

    /**
     * 重命名文件, 如需要异步或批量操作 请直接使用 [rest] [web] [pcs] 的 api
     * @param path 源路径
     * @param newname 新文件名
     * @param ondup 文件冲突策略
     * @see BaiduNetDiskRESTful.move
     */
    public suspend fun rename(path: String, newname: String, ondup: OnDupType): NetDiskOpera {
        return rest.move(OperaFileInfo(path, newname), ondup = ondup)
    }

    /**
     * 删除文件, 如需要异步或批量操作 请直接使用 [rest] [web] [pcs] 的 api
     * @param path 源路径
     */
    public suspend fun delete(path: String): NetDiskOpera {
        return rest.delete(OperaFileInfo(path))
    }

    /**
     * 搜索文件
     * @param key 关键词
     * @param dir 起始目录, / 网盘根目录, 为空时是 APP 数据目录
     * @param page 页码 从 1 开始，一页数目 1000
     * @see BaiduNetDiskRESTful.search
     */
    public suspend fun search(key: String, dir: String, page: Int): NetDiskFileList {
        return rest.search(key, dir, true, page)
    }

    /**
     * 列出文件，如需要递归或类别搜索 请直接使用 [rest] [web] [pcs] 的 api
     * @param path 起始目录, / 网盘根目录, 为空时是 APP 数据目录
     * @param start 起始编号，从 0开始
     * @see BaiduNetDiskRESTful.search
     */
    public suspend fun list(path: String, start: Int): NetDiskFileList {
        return rest.list(
            start = start,
            option = NetDiskOption(path = path, order = OrderType.TIME, desc = true, recursion = true)
        )
    }

    /**
     * 链接下载文件
     * @param file 要下载的文件信息
     * @see download
     * @see BaiduPersonalCloudStorage.load
     * @see BaiduPersonalCloudStorage.download
     */
    public suspend fun link(file: NetDiskFileInfo, block: HttpRequestBuilder.() -> Unit): HttpStatement {
        return if (file is NetDiskFileMeta) {
            pcs.load(dlink = file.dlink, block = block)
        } else {
            pcs.download(path = file.path, block = block)
        }
    }

    /**
     * 下载文件 断点续传
     * @param file 要下载的文件信息
     * @param range 要下载的序列，为 null 时，将下载全部数据
     * @see link
     */
    public suspend fun download(file: NetDiskFileInfo, range: IntRange?): ByteArray {
        return link(file = file) {
            header(HttpHeaders.Range, range?.run { "${first}-${last}" })
        }.body()
    }

    /**
     * 下载文件 进度监听
     * @param file 要下载的文件信息
     * @param listener 进度回调
     * @see link
     */
    public suspend fun download(file: NetDiskFileInfo, listener: ProgressListener): ByteArray {
        return link(file = file) {
            onDownload(listener)
        }.body()
    }

    /**
     * 刷新 PCS Host
     */
    public suspend fun host(): PCSServer {
        val upload = pcs.host()
        pcs.upload = upload
        return upload
    }
}