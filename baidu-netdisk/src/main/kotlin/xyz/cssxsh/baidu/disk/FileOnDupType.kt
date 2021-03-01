package xyz.cssxsh.baidu.disk

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = FileOnDupType.Serializer::class)
enum class FileOnDupType {
    FAIL,
    NEWCOPY,
    OVERWRITE,
    SKIP;

    override fun toString(): String = name.toLowerCase()

    companion object Serializer : KSerializer<FileOnDupType> {
        override val descriptor: SerialDescriptor
            get() = buildSerialDescriptor("FileOnDupTypeSerializer", SerialKind.ENUM)

        override fun serialize(encoder: Encoder, value: FileOnDupType) =
            encoder.encodeString(value.name.toLowerCase())

        override fun deserialize(decoder: Decoder): FileOnDupType =
            valueOf(decoder.decodeString().toUpperCase())
    }
}