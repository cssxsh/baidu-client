package xyz.cssxsh.baidu.aip.censor

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*

@Serializable(with = ConclusionType.Serializer::class)
public enum class ConclusionType {
    NONE,
    COMPLIANCE,
    NON_COMPLIANCE,
    SUSPECTED,
    FAILURE;

    public companion object Serializer : KSerializer<ConclusionType> by OrdinalSerializer()
}