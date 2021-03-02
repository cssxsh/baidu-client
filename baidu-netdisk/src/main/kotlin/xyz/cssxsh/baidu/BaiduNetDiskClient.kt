package xyz.cssxsh.baidu

import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import xyz.cssxsh.baidu.disk.*
import java.io.File

open class BaiduNetDiskClient(
    override val appId: Long,
    override val appName: String,
    override val appKey: String,
    override val secretKey: String
) : AbstractNetDiskClient(emptyList()) {

    suspend fun createDir(path: String): NetDiskFileInfo =
        createFile(path = path, size = 0, isDir = true, uploadId = null)

    suspend fun uploadFile(file: File, path: String = file.name): NetDiskFileInfo {
        val user = getUserInfo()
        check(file.isFile) {
            "${file.absolutePath}不是文件"
        }
        check(file.length() <= user.getUpdateLimit()) {
            "超过当前用户${user}上传上限"
        }
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

    suspend fun rapidUploadFile(info: RapidUploadInfo, rename: RenameType = RenameType.COVER): NetDiskFileInfo =
        rapidUpload(info.content, info.slice, info.length, info.path, rename).info

    suspend fun getRapidUploadInfo(path: String): RapidUploadInfo {
        lateinit var content: String
        lateinit var length: String
        lateinit var slice: String
        useHttpClient { client ->
            client.get<HttpResponse>(downloadFileUrl(path = withAppDataFolder(path))) {
                header(HttpHeaders.Range, "bytes=0-${NetDisk.SLICE_SIZE - 1}")
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