package xyz.cssxsh.baidu.api

import io.ktor.util.encodeBase64
import io.ktor.util.decodeBase64Bytes
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import java.time.*
import java.time.format.*
import kotlin.reflect.*

/**
 * boolean 转为 数字 1 和 0
 */
public fun Boolean.toInt(): Int = if (this) 1 else 0

@PublishedApi
internal const val IGNORE_UNKNOWN_KEYS: String = "xyz.cssxsh.baidu.json.ignore"

/**
 * 根据百度API调整的序列化
 */
public val BaiduJson: Json = Json {
    isLenient = true
    ignoreUnknownKeys = System.getProperty(IGNORE_UNKNOWN_KEYS, "true").toBoolean()
    serializersModule = SerializersModule {
        contextual(OffsetDateTimeSerializer)
    }
}

/**
 * 将 bytes 序列化为 base64 字符串
 */
public object ByteArrayToBase64Serializer : KSerializer<ByteArray> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(this::class.qualifiedName!!, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ByteArray) {
        encoder.encodeString(value.encodeBase64())
    }

    override fun deserialize(decoder: Decoder): ByteArray {
        return decoder.decodeString().decodeBase64Bytes()
    }
}

/**
 * 将 0 和 1 解析为 Boolean 值 false 和 true
 */
public object NumberToBooleanSerializer : KSerializer<Boolean> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(this::class.qualifiedName!!, PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Boolean) {
        encoder.encodeInt(value.toInt())
    }

    override fun deserialize(decoder: Decoder): Boolean {
        return decoder.decodeInt() != 0
    }
}

/**
 * 将 日期时间 序列化为字符串
 */
public object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {

    private val format: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(OffsetDateTime::class.qualifiedName!!, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeString(value.format(format))
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        return OffsetDateTime.parse(decoder.decodeString(), format)
    }
}

/**
 * 将 日期时间 序列化为 Timestamp (Long)
 */
public object TimestampSerializer : KSerializer<OffsetDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(OffsetDateTime::class.qualifiedName!!, PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeLong(value.toEpochSecond())
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(decoder.decodeLong()), ZoneId.systemDefault())
    }
}

/**
 * 将 枚举 序列化为 ordinal 数字
 */
public class OrdinalSerializer<E : Enum<E>>(kClass: KClass<E>, private val values: Array<E>) : KSerializer<E> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(kClass.qualifiedName!!, PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: E) {
        encoder.encodeInt(value.ordinal)
    }

    override fun deserialize(decoder: Decoder): E {
        val index = decoder.decodeInt().coerceAtLeast(0)
        return requireNotNull(values.getOrNull(index)) { "$index not in ${values.asList()}" }
    }
}

/**
 * 将 枚举 序列化为 ordinal 数字
 * @see OrdinalSerializer
 */
@Suppress("FunctionName")
public inline fun <reified E : Enum<E>> OrdinalSerializer(): OrdinalSerializer<E> {
    return OrdinalSerializer(kClass = E::class, values = enumValues())
}

/**
 * 将 枚举 序列化为 name 字符串
 */
public class LowerCaseSerializer<E : Enum<E>>(kClass: KClass<E>, private val values: Array<E>) : KSerializer<E> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(kClass.qualifiedName!!, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: E) {
        encoder.encodeString(value.name.lowercase())
    }

    override fun deserialize(decoder: Decoder): E {
        val text = decoder.decodeString().uppercase()
        return requireNotNull(values.find { it.name == text }) { "$text not in ${values.asList()}" }
    }
}

/**
 * 将 枚举 序列化为 name 字符串
 * @see LowerCaseSerializer
 */
@Suppress("FunctionName")
public inline fun <reified E : Enum<E>> LowerCaseSerializer(): LowerCaseSerializer<E> {
    return LowerCaseSerializer(kClass = E::class, values = enumValues())
}