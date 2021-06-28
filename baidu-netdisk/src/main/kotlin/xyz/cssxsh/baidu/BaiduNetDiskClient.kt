package xyz.cssxsh.baidu

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import xyz.cssxsh.baidu.disk.*
import java.io.File

/**
 * 构建客户端的参数需要到 [百度网盘开放中心](https://pan.baidu.com/union/apply) 申请
 */
open class BaiduNetDiskClient(config: BaiduAuthConfig) : AbstractNetDiskClient(), BaiduAuthConfig by config {
    constructor(appId: Long, appName: String, appKey: String, secretKey: String): this(object : BaiduAuthConfig {
        override val appName: String = appName
        override val appId: Long = appId
        override val appKey: String = appKey
        override val secretKey: String = secretKey
    })

    /**
     * 创建一个目录
     */
    suspend fun createDir(path: String): NetDiskFileInfo {
        return createFile(path, 0, true, null)
    }

    /**
     * 分段上传一个文件，默认路径是/apps/${appName}/${file.name}，文件存在会报错
     */
    suspend fun uploadFile(file: File, path: String = file.name): NetDiskFileInfo {
        check(file.isFile) { "${file.absolutePath}不是文件" }

        val user = getUserInfo()
        check(file.length() <= user.getUpdateLimit()) { "超过当前用户${user}上传上限" }

        val temp = ByteArray(user.getSuperLimit())
        val blocks = file.getBlockList(buffer = temp)
        val pre = preCreate(
            path = path,
            size = file.length(),
            isDir = false,
            blocks = blocks.map { it.md5 },
        )
        if (pre.type == CreateReturnType.EXIST) {
            return requireNotNull(pre.info) { pre.toString() }
        }
        if (pre.blocks.isEmpty()) {
            blocks[0].exist = false
        } else {
            pre.blocks.forEach { index ->
                blocks[index].exist = false
            }
        }
        blocks.filterNot { it.exist }.mapIndexed { index, info ->
            file.readBlock(buffer = temp, offset = info.offset, length = info.length)
            superFile(
                path = path,
                uploadId = pre.uploadId,
                index = index,
                data = temp,
                size = info.length
            )
        }
        return createFile(
            path = path,
            size = file.length(),
            isDir = false,
            blocks = blocks.map { it.md5 },
            uploadId = pre.uploadId
        )
    }

    /**
     * 快速上传文件 需要Rapid信息，默认为覆盖模式
     */
    suspend fun rapidUploadFile(info: RapidUploadInfo, rename: RenameType = RenameType.COVER): NetDiskFileInfo {
        return rapidUpload(info.content, info.slice, info.length, info.path, rename).info
    }

    /**
     * 获取文件的Rapid信息
     */
    suspend fun getRapidUploadInfo(path: String): RapidUploadInfo {
        lateinit var content: String
        lateinit var length: String
        lateinit var slice: String
        useHttpClient { client ->
            client.get<HttpResponse>(downloadFileUrl(path = withAppDataFolder(path))) {
                header(HttpHeaders.Range, "bytes=0-${SLICE_SIZE - 1}")
            }.apply {
                content = requireNotNull(headers[HttpHeaders.ETag])
                length = requireNotNull(headers[HttpHeaders.ContentRange]).substringAfterLast('/')
            }.readBytes().let { bytes ->
                slice = digestMd5(input = bytes)
            }
        }
        return RapidUploadInfo(content = content, slice = slice, length = length.toLong(), path = path)
    }
}