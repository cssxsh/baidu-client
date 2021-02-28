package xyz.cssxsh.baidu.disk

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = VipType.Serializer::class)
enum class VipType {
    ORDINARY,
    MEMBER,
    SUPER_MEMBER;

    companion object Serializer : KSerializer<VipType> {
        override val descriptor: SerialDescriptor
            get() = buildSerialDescriptor("VipTypeSerializer", SerialKind.ENUM)

        override fun serialize(encoder: Encoder, value: VipType) = encoder.encodeInt(value.ordinal)

        override fun deserialize(decoder: Decoder): VipType =
            requireNotNull(values().getOrNull(decoder.decodeInt())) { "${decoder.decodeInt()} not in ${values()}" }
    }
}