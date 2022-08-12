package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 文件操作结果
 * @param taskId 任务ID
 * @param info 操作结果
 */
@Serializable
public data class NetDiskOpera(
    @SerialName("info")
    val info: List<Item>,
    @SerialName("taskid")
    override val taskId: Long = 0,
    @SerialName("errmsg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo, NetDiskTaskInfo {

    /**
     * @param path 文件路径
     */
    @Serializable
    public data class Item(
        @SerialName("path")
        val path: String,
        @SerialName("errmsg")
        override val errorMessage: String = "",
        @SerialName("errno")
        override val errorNo: Int = 0,
        @SerialName("request_id")
        override val requestId: String = ""
    ) : NetDiskErrorInfo
}