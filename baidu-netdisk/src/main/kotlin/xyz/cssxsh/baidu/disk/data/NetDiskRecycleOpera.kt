package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 回收站操作结果
 * @param failed 操作失败文件ID
 */
@Serializable
public data class NetDiskRecycleOpera(
    @SerialName("faillist")
    val failed: List<Long> = emptyList(),
    @SerialName("taskid")
    override val taskId: Long = 0,
    @SerialName("show_msg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo, NetDiskTaskInfo