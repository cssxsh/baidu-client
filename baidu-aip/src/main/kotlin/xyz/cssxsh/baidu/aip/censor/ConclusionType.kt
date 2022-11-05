package xyz.cssxsh.baidu.aip.censor

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*

/**
 * 推断结果
 * @property NONE 无
 * @property COMPLIANCE 合规
 * @property NON_COMPLIANCE 不合规
 * @property SUSPECTED 疑似
 * @property FAILURE 审核失败
 */
@Serializable(with = ConclusionType.Serializer::class)
public enum class ConclusionType {
    NONE,
    COMPLIANCE,
    NON_COMPLIANCE,
    SUSPECTED,
    FAILURE;

    internal companion object Serializer : KSerializer<ConclusionType> by OrdinalSerializer()
}