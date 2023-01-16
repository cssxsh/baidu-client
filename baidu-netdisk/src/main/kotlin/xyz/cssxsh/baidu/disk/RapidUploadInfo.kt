package xyz.cssxsh.baidu.disk

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.io.File
import java.security.*
import java.time.*

/**
 * 快传信息
 * @param content 文件MD5
 * @param slice 文件校验段的MD5，校验段对应文件前256KB
 * @param length 文件长度
 * @param path 文件路径
 * @param created 文件创建时间
 * @param modified 文件修改时间
 */
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
    @SerialName("local_ctime")
    @Serializable(TimestampSerializer::class)
    val created: OffsetDateTime? = null,
    @SerialName("local_mtime")
    @Serializable(TimestampSerializer::class)
    val modified: OffsetDateTime? = null,
) {
    public companion object {
        /**
         * 从秒传码反序列化
         */
        @JvmStatic
        public fun parse(code: String): RapidUploadInfo {
            val parts = code.split('#')
            return when (parts.size) {
                4 -> parts.let { (content, slice, length, path) ->
                    RapidUploadInfo(content = content, slice = slice, length = length.toLong(), path = path)
                }
                3 -> parts.let { (content, length, path) ->
                    RapidUploadInfo(content = content, slice = "", length = length.toLong(), path = path)
                }
                else -> throw IllegalArgumentException("秒传码格式错误")
            }
        }

        /**
         * 从文件计算得到快传信息
         */
        @JvmStatic
        public fun calculate(file: File): RapidUploadInfo {
            val digest = MessageDigest.getInstance("md5")
            val buffer = ByteArray(SLICE_SIZE)
            val (content, slice) = file.inputStream().use { input ->
                var size = input.read(buffer)
                digest.update(buffer, 0, size)
                val slice = digest.digest()
                digest.update(buffer, 0, size)

                do {
                    size = input.read(buffer)
                    if (size <= 0) {
                        break
                    } else {
                        digest.update(buffer, 0, size)
                    }
                } while (true)
                digest.digest().toHexString() to slice.toHexString()
            }

            val modified = OffsetDateTime.ofInstant(Instant.ofEpochSecond(file.lastModified()), ZoneId.systemDefault())

            return RapidUploadInfo(
                content = content,
                slice = slice,
                length = file.length(),
                path = file.name,
                created = modified,
                modified = modified
            )
        }
    }

    /**
     * 序列化为秒传码
     */
    public fun format(encrypt: Boolean = false): String {
        return if (encrypt) {
            "${content.encryptMD5()}#${slice.encryptMD5()}#$length#$path"
        } else {
            "$content#$slice#$length#$path"
        }
    }
}