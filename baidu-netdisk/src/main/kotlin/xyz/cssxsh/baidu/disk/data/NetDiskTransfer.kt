package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 转存结果
 * @param info 转存文件结果
 * @param taskId 任务ID
 */
@Serializable
public data class NetDiskTransfer(
    @SerialName("extra")
    val extra: Extra? = null,
    @SerialName("info")
    val info: List<Info> = emptyList(),
    @SerialName("task_id")
    override val taskId: Long = 0,
    @SerialName("err_msg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String = ""
) : NetDiskErrorInfo, NetDiskTaskInfo {

    @Serializable
    public data class Info(
        @SerialName("fsid")
        val fid: Long,
        @SerialName("path")
        val path: String,
        @SerialName("err_msg")
        override val errorMessage: String = "",
        @SerialName("errno")
        override val errorNo: Int = 0,
        @SerialName("request_id")
        override val requestId: String = ""
    ) : NetDiskErrorInfo


    @Serializable
    public data class Extra(
        @SerialName("list")
        val list: List<Change>
    )

    @Serializable
    public data class Change(
        @SerialName("from")
        val from: String,
        @SerialName("from_fs_id")
        val fid: Long,
        @SerialName("to")
        val to: String
    )
}