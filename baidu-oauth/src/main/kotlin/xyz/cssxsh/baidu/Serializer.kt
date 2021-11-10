@file:OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)

package xyz.cssxsh.baidu

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlin.reflect.*


@Serializer(forClass = Boolean::class)
object NumberToBooleanSerializer : KSerializer<Boolean> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("NumberToBooleanSerializer", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Boolean) = encoder.encodeInt(value.toInt())

    override fun deserialize(decoder: Decoder): Boolean = decoder.decodeInt() != 0
}

class OrdinalSerializer<E : Enum<E>>(kClass: KClass<E>, private val values: Array<E>) : KSerializer<E> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor(kClass.qualifiedName!!, SerialKind.ENUM)

    override fun serialize(encoder: Encoder, value: E) = encoder.encodeInt(value.ordinal)

    override fun deserialize(decoder: Decoder): E =
        requireNotNull(values.getOrNull(decoder.decodeInt())) { "${decoder.decodeInt()} not in ${values.asList()}" }
}

inline fun <reified E : Enum<E>> OrdinalSerializer() = OrdinalSerializer(kClass = E::class, values = enumValues())

class LowerCaseSerializer<E : Enum<E>>(kClass: KClass<E>, private val values: Array<E>) : KSerializer<E> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor(kClass.qualifiedName!!, SerialKind.ENUM)

    override fun serialize(encoder: Encoder, value: E) = encoder.encodeString(value.name.lowercase())

    override fun deserialize(decoder: Decoder): E {
        val text = decoder.decodeString().uppercase()
        return requireNotNull(values.first { it.name == text }) { "$text not in ${values.asList()}" }
    }
}

inline fun <reified E : Enum<E>> LowerCaseSerializer() = LowerCaseSerializer(kClass = E::class, values = enumValues())