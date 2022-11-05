package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 文件操作结果数据
 * @param extra 结果
 */
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

    /**
     * 文件操作结果
     * @param list 结果
     */
    @Serializable
    public data class Extra<T>(
        @SerialName("list")
        val list: List<T>
    )
}