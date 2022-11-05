package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 回收站文件列表
 * @param list 文件列表
 * @param timestamp 时间戳
 */
@Serializable
public data class NetDiskRecycleList(
    @SerialName("list")
    override val list: List<NetDiskRecycleFile> = emptyList(),
    @SerialName("timestamp")
    val timestamp: Long = 0,
    @SerialName("show_msg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo, NetDiskFileData