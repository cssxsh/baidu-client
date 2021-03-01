package xyz.cssxsh.baidu.disk

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = AsyncType.Serializer::class)
enum class AsyncType {
    SYNC,
    AUTO,
    ASYNC;

    companion object Serializer : KSerializer<AsyncType> {
        override val descriptor: SerialDescriptor
            get() = buildSerialDescriptor("AsyncTypeSerializer", SerialKind.ENUM)

        override fun serialize(encoder: Encoder, value: AsyncType) = encoder.encodeInt(value.ordinal)

        override fun deserialize(decoder: Decoder): AsyncType =
            requireNotNull(values().getOrNull(decoder.decodeInt())) { "${decoder.decodeInt()} not in ${values().toList()}" }
    }
}