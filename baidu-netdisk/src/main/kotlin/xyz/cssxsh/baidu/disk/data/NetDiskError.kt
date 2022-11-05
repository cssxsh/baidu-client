package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 网盘错误信息
 */
@Serializable
public data class NetDiskError(
    @SerialName("errmsg")
    override val errorMessage: String,
    @SerialName("errno")
    override val errorNo: Int,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo