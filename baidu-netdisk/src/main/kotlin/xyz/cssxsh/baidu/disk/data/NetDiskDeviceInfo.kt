package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*

/**
 * 网盘关联设备信息
 */
@Serializable
public data class NetDiskDeviceInfo(
    @SerialName("device_id")
    val id: String,
    @SerialName("errmsg")
    override val errorMessage: String,
    @SerialName("errno")
    override val errorNo: Int,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo