package xyz.cssxsh.baidu.disk

import io.ktor.http.*
import io.ktor.utils.io.core.*
import java.security.MessageDigest
import java.util.*

/**
 * 256KB
 */
public const val SLICE_SIZE: Int = 256 shl 10

public val LAZY_BLOCKS: List<String> = listOf("5910a591dd8fc18c32a8f3df4fdc1761", "a5fc157d78e6ad1c7e114b056c92821e")

internal fun ParametersBuilder.appendParameter(key: String, value: Any?) {
    value?.let { append(key, it.toString()) }
}

public typealias BodyBuilder = BytePacketBuilder.() -> Unit

internal fun ByteArray.toHexString(): String {
    return fold(Formatter()) { formatter, byte -> formatter.format("%02x", byte) }
        .toString()
}

internal fun ByteArray.md5(): ByteArray {
    val digest = MessageDigest.getInstance("md5")
    return digest.digest(this)
}

internal fun String.encryptMD5(): String {
    val md5 = this
    return buildString {
        append(md5.subSequence(8, 16))
        append(md5.subSequence(0, 8))
        append(md5.subSequence(24, 32))
        append(md5.subSequence(16, 24))

        repeat(32) { index ->
            setCharAt(index, (get(index).digitToInt(16) xor (15 and index)).digitToChar(16))
        }
        setCharAt(9, (get(9).digitToInt(16) + 'g'.code).toChar())
    }
}

