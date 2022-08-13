package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

@Serializable
public data class NetDiskError(
    @SerialName("errmsg")
    override val errorMessage: String,
    @SerialName("errno")
    override val errorNo: Int,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo