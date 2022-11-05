package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*

/**
 * 网盘关联设备检查
 */
@Serializable
public data class NetDiskDeviceCheck(
    @SerialName("status")
    @Serializable(NumberToBooleanSerializer::class)
    val status: Boolean,
    @SerialName("errmsg")
    override val errorMessage: String,
    @SerialName("errno")
    override val errorNo: Int,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo