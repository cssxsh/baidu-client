package xyz.cssxsh.baidu

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

    suspend fun uploadFile(file: File, path: String = "${appDataFolder}/${file.name}"): NetDiskFileInfo {
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

    suspend fun loadFile(size: Long, content: String, slice: String, path: String): NetDiskFileInfo {
        val pre = preCreate(
            path = path,
            size = size,
            isDir = false,
            blocks = emptyList(),
            content = content,
            slice = slice,
            rename = RenameType.BLOCK
        )
        check(pre.type == CreateReturnType.EXIST) { pre.toString() }
        return listFileById(listOf(pre.info!!.id)).list.single()
    }
}