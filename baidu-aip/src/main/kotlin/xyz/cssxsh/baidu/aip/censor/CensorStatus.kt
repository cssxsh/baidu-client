package xyz.cssxsh.baidu.aip.censor


sealed interface CensorStatus {
    val errorCode: Long?
    val errorMessage: String?
    val conclusion: String
    val conclusionType: ConclusionType
}