package xyz.cssxsh.baidu.disk

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = CreateReturnType.Serializer::class)
enum class CreateReturnType {
    TEMP_,
    NOT_EXIST,
    EXIST;

    companion object Serializer : KSerializer<CreateReturnType> {
        override val descriptor: SerialDescriptor
            get() = buildSerialDescriptor("CategoryTypeSerializer", SerialKind.ENUM)

        override fun serialize(encoder: Encoder, value: CreateReturnType) = encoder.encodeInt(value.ordinal)

        override fun deserialize(decoder: Decoder): CreateReturnType =
            requireNotNull(values().getOrNull(decoder.decodeInt())) { "${decoder.decodeInt()} not in ${values()}" }
    }
}