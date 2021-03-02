package xyz.cssxsh.baidu

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive
import xyz.cssxsh.baidu.disk.*
import java.io.File
import java.io.RandomAccessFile
import java.security.MessageDigest

typealias RequestIdType = JsonPrimitive

fun NetDiskClient.withAppDataFolder(path: String?) =
    if (path.orEmpty().startsWith("/")) path.orEmpty() else "${appDataFolder}/${path}"

internal fun NetDiskUserInfo.getUpdateLimit(): Long = when (vipType) {
    // 4GB
    VipType.ORDINARY -> 4L shl 30
    // 10G
    VipType.MEMBER -> 10L shl 30
    // 20G
    VipType.SUPER_MEMBER -> 20L shl 30
}

internal fun NetDiskUserInfo.getSuperLimit(): Int = when (vipType) {
    // 4MB
    VipType.ORDINARY -> 4 shl 20
    // 10G
    VipType.MEMBER -> 16 shl 20
    // 20G
    VipType.SUPER_MEMBER -> 32 shl 20
}

internal data class BlockInfo(
    val offset: Long,
    val length: Int,
    val md5: String,
    var exist: Boolean,
)

internal fun digestMd5(input: ByteArray, size: Int = input.size): String {
    return MessageDigest.getInstance("md5").also {
        it.update(input, 0, size)
    }.digest().asUByteArray().joinToString("") {
        """%02x""".format(it.toInt())
    }
}

internal fun File.getBlockList(buffer: ByteArray, size: Int = buffer.size): List<BlockInfo> {
    return (0 until length() step size.toLong()).map { offset ->
        val length = minOf(size, (length() - offset).toInt())
        readBlock(buffer = buffer, offset = offset, length = length)
        BlockInfo(
            offset = offset,
            length = length,
            md5 = digestMd5(buffer, length),
            exist = true
        )
    }
}

internal fun File.readBlock(buffer: ByteArray, offset: Long = 0, length: Int = buffer.size) {
    RandomAccessFile(this, "r").use {
        it.seek(offset)
        it.read(buffer, 0, length)
    }
}

internal fun File.digestContentMd5(blockSize: Int = 4 shl 20): String {
    return MessageDigest.getInstance("md5").also {
        forEachBlock(blockSize) { buffer, read ->
            it.update(buffer, 0, read)
        }
    }.digest().asUByteArray().joinToString("") {
        """%02x""".format(it.toInt())
    }
}

internal fun File.digestSliceMd5(): String {
    val temp = ByteArray(NetDisk.SLICE_SIZE.toLong().coerceAtMost(length()).toInt())
    readBlock(buffer = temp)
    return digestMd5(input = temp)
}

@Serializable
data class RapidUploadInfo(
    @SerialName("content")
    val content: String,
    @SerialName("slice")
    val slice: String,
    @SerialName("length")
    val length: Long,
    @SerialName("path")
    val path: String,
) {
    fun format(): String = "$content#$slice#$length#$path"

    companion object {
        fun parse(code: String): RapidUploadInfo = code.split('#').let { (content, slice, length, path) ->
            RapidUploadInfo(content = content, slice = slice, length = length.toLong(), path = path)
        }
    }
}

fun File.getRapidUploadInfo(path: String = name) =
    RapidUploadInfo(content = digestContentMd5(), slice = digestSliceMd5(), length = length(), path = path)

