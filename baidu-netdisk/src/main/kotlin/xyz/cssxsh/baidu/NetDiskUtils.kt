package xyz.cssxsh.baidu

import xyz.cssxsh.baidu.disk.*
import java.io.File
import java.io.RandomAccessFile
import java.security.MessageDigest

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
        RandomAccessFile(this, "r").use {
            it.seek(offset)
            it.read(buffer, 0, length)
        }
        BlockInfo(
            offset = offset,
            length = length,
            md5 = digestMd5(buffer, length),
            exist = true
        )
    }
}

internal fun File.readBlock(buffer: ByteArray, offset: Long, length: Int) {
    RandomAccessFile(this, "r").use {
        it.seek(offset)
        it.read(buffer, 0, length)
    }
}

fun File.digestContentMd5(): String = digestMd5(input = readBytes())

fun File.digestSliceMd5(): String {
    val temp = ByteArray(NetDiskApi.SLICE_SIZE.toLong().coerceAtMost(length()).toInt())
    println(NetDiskApi.SLICE_SIZE)
    println(temp.size)
    readBlock(buffer = temp, 0, length = temp.size)
    return digestMd5(input = temp)
}