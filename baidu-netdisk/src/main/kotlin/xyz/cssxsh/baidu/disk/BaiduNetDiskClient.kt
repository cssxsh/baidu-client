package xyz.cssxsh.baidu.disk

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.baidu.disk.data.*
import xyz.cssxsh.baidu.oauth.*
import java.io.*

/**
 * 构建客户端的参数需要到 [百度网盘开放中心](https://pan.baidu.com/union/apply) 申请
 */
public open class BaiduNetDiskClient(override val config: BaiduAuthConfig) : AbstractNetDiskClient() {
    public constructor(config: BaiduAuthConfig, token: AuthorizeAccessToken) : this(config) {
        save(token = token)
    }

    /**
     * 创建一个目录
     */
    public suspend fun createDir(path: String): NetDiskFileInfo {
        return createFile(path = path, size = 0, isDir = true, rename = RenameType.NO)
    }

    /**
     * 分段上传一个文件，默认路径是/apps/${appName}/${file.name}，文件存在会报错
     */
    public suspend fun uploadFile(file: File, path: String = file.name): NetDiskFileInfo {
        check(file.isFile) { "${file.absolutePath}不是文件" }

        val user = getUserInfo()
        check(file.length() <= user.vip.updateLimit) { "超过当前用户${user}上传上限" }

        val temp = ByteArray(user.vip.superLimit)
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
            for (index in pre.blocks) {
                blocks[index].exist = false
            }
        }
        for ((index, info) in blocks.filterNot { it.exist }.withIndex()) {
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
    public suspend fun rapidUploadFile(info: RapidUploadInfo, rename: RenameType = RenameType.COVER): NetDiskFileInfo {
        val result = rapidUpload(info.content, info.slice, info.length, info.path, rename)
        return requireNotNull(result.info) { "快速上传失败 ${result.errno}" }
    }

    /**
     * 获取文件的Rapid信息
     */
    public suspend fun getRapidUploadInfo(path: String): RapidUploadInfo {
        return useHttpClient { client ->
            client.prepareGet(downloadFileUrl(path = path)) {
                header(HttpHeaders.Range, "bytes=0-${SLICE_SIZE - 1}")
            }.execute { response ->
                RapidUploadInfo(
                    content = requireNotNull(response.headers[HttpHeaders.ETag]) { "Not Found ETag" },
                    slice = digestMd5(input = response.body()),
                    length = requireNotNull(response.headers[HttpHeaders.ContentRange]) { "Not Found ContentRange" }
                        .substringAfterLast('/')
                        .toLong(),
                    path = path
                )
            }
        }
    }
}