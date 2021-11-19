package xyz.cssxsh.baidu.aip.censor

import kotlinx.serialization.*
import xyz.cssxsh.baidu.*

@Serializable(with = ConclusionType.Serializer::class)
enum class ConclusionType {
    NONE,
    COMPLIANCE,
    NON_COMPLIANCE,
    SUSPECTED,
    FAILURE;

    companion object Serializer : KSerializer<ConclusionType> by OrdinalSerializer()
}