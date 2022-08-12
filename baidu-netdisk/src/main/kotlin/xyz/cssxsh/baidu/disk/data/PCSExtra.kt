package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

@Serializable
public data class PCSExtra<T>(
    @SerialName("extra")
    val extra: Extra<T>,
    @SerialName("error_code")
    override val errorCode: Int = 0,
    @SerialName("error_msg")
    override val errorMessage: String = "",
    @SerialName("request_id")
    override val requestId: Long
) : PCSErrorInfo {

    @Serializable
    public data class Extra<T>(
        @SerialName("list")
        val list: List<T>
    )
}