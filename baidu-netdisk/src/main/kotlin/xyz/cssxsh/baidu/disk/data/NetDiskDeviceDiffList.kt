package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*

@Serializable
public data class NetDiskDeviceDiffList(
    @SerialName("cursor")
    val cursor: String = "",
    @SerialName("device_list")
    val devices: List<NetDiskDeviceDiff> = emptyList(),
    @SerialName("has_more")
    @Serializable(NumberToBooleanSerializer::class)
    val hasMore: Boolean = false,
    @SerialName("errmsg")
    override val errorMessage: String,
    @SerialName("errno")
    override val errorNo: Int,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo