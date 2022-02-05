package xyz.cssxsh.baidu.aip.censor

import xyz.cssxsh.baidu.aip.*

public sealed interface CensorStatus: AipExceptionInfo {
    public val conclusion: String
    public val conclusionType: ConclusionType
}