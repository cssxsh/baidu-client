package xyz.cssxsh.baidu.disk

import kotlinx.serialization.*
import java.io.File
import java.io.RandomAccessFile
import java.security.MessageDigest

internal data class BlockInfo(
    val offset: Long,
    val length: Int,
    val md5: String,
    var exist: Boolean,
)

internal fun digestMd5(input: ByteArray, size: Int = input.size): String {
    return MessageDigest.getInstance("md5").also {
        it.update(input, 0, size)
    }.digest().joinToString("") {
        """%02x""".format(it.toInt() and 0xFF)
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
    }.digest().joinToString("") {
        """%02x""".format(it.toInt() and 0xFF)
    }
}

internal fun File.digestSliceMd5(): String {
    val temp = ByteArray(if (SLICE_SIZE < length()) length().toInt() else SLICE_SIZE)
    readBlock(buffer = temp)
    return digestMd5(input = temp)
}

@Serializable
public data class RapidUploadInfo(
    @SerialName("content")
    val content: String,
    @SerialName("slice")
    val slice: String,
    @SerialName("length")
    val length: Long,
    @SerialName("path")
    val path: String,
) {
    public companion object {
        @JvmStatic
        public fun parse(code: String): RapidUploadInfo {
            return code.split('#').let { (content, slice, length, path) ->
                RapidUploadInfo(content = content, slice = slice, length = length.toLong(), path = path)
            }
        }

        @JvmStatic
        public fun calculate(file: File): RapidUploadInfo {
            return RapidUploadInfo(
                content = file.digestContentMd5(),
                slice = file.digestSliceMd5(),
                length = file.length(),
                path = file.name
            )
        }
    }

    public fun format(): String = "$content#$slice#$length#$path"
}
