package xyz.cssxsh.baidu.aip.censor


public sealed interface CensorStatus {
    public val errorCode: Long?
    public val errorMessage: String?
    public val conclusion: String
    public val conclusionType: ConclusionType
}