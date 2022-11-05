package xyz.cssxsh.baidu.aip.censor

import xyz.cssxsh.baidu.aip.*

/**
 * 审核状态
 * @property conclusion 推断
 * @property conclusion 推断结果
 */
public sealed interface CensorStatus : AipExceptionInfo {
    public val conclusion: String
    public val conclusionType: ConclusionType
}