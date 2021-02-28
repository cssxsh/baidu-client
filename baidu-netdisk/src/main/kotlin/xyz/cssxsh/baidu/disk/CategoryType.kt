package xyz.cssxsh.baidu.disk

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * [document](https://pan.baidu.com/union/document/basic#%E8%8E%B7%E5%8F%96%E6%96%87%E4%BB%B6%E5%88%97%E8%A1%A8)
 */
@Serializable(with = CategoryType.Serializer::class)
enum class CategoryType {
    NONE,
    VIDEO,
    AUDIO,
    PICTURES,
    DOCUMENTS,
    APPLICATIONS,
    OTHERS,
    SEEDS;

    companion object Serializer : KSerializer<CategoryType> {
        override val descriptor: SerialDescriptor
            get() = buildSerialDescriptor("CategoryTypeSerializer", SerialKind.ENUM)

        override fun serialize(encoder: Encoder, value: CategoryType) = encoder.encodeInt(value.ordinal)

        override fun deserialize(decoder: Decoder): CategoryType =
            requireNotNull(values().getOrNull(decoder.decodeInt())) { "${decoder.decodeInt()} not in ${values()}" }
    }
}