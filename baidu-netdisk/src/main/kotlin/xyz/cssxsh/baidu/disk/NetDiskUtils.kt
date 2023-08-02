package xyz.cssxsh.baidu.disk

import io.ktor.http.*
import io.ktor.utils.io.core.*
import java.security.*
import java.util.*

/**
 * 256KB
 */
public const val SLICE_SIZE: Int = 256 shl 10

public val LAZY_BLOCKS: List<String> = listOf("5910a591dd8fc18c32a8f3df4fdc1761", "a5fc157d78e6ad1c7e114b056c92821e")

@PublishedApi
internal fun ParametersBuilder.appendParameter(key: String, value: Any?) {
    when (value) {
        null -> return
        is String -> append(key, value)
        else -> append(key, value.toString())
    }
}

public typealias BodyBuilder = BytePacketBuilder.() -> Unit

@PublishedApi
internal fun ByteArray.toHexString(): String {
    return fold(Formatter()) { formatter, byte -> formatter.format("%02x", byte) }
        .toString()
}

@PublishedApi
internal fun ByteArray.md5(): ByteArray {
    val digest = MessageDigest.getInstance("md5")
    return digest.digest(this)
}

@PublishedApi
internal fun String.encryptMD5(flag: Boolean = true): String {
    check(length == 32)
    val md5 = this
    return buildString {
        append(md5.subSequence(8, 16))
        append(md5.subSequence(0, 8))
        append(md5.subSequence(24, 32))
        append(md5.subSequence(16, 24))

        repeat(32) { index ->
            val char = get(index)
                .digitToInt(16)
                .xor(15 and index)
                .digitToChar(16)
                .lowercaseChar()
            setCharAt(index, char)
        }
        if (flag) {
            val special = 'g'
                .plus(get(9).digitToInt(16))
            setCharAt(9, special)
        }
    }
}

@PublishedApi
internal fun String.decryptMD5(): String {
    check(length == 32)
    val cipher = this
    return buildString {
        append(cipher.subSequence(8, 16))
        append(cipher.subSequence(0, 8))
        append(cipher.subSequence(24, 32))
        append(cipher.subSequence(16, 24))

        if (get(1) >= 'g') {
            val special = get(1)
                .minus('g')
                .digitToChar(16)
            setCharAt(1, special)
        }
        repeat(32) { index ->
            val char = get(index)
                .digitToInt(16)
                .xor(index xor 8 and 15)
                .digitToChar(16)
                .lowercaseChar()
            setCharAt(index, char)
        }
    }
}
