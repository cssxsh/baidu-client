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

    /**
     * 转存内容
     * @param fid 文件ID
     * @param path 文件路径
     */
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

    /**
     * 额外信息
     */
    @Serializable
    public data class Extra(
        @SerialName("list")
        val list: List<Change>
    )

    /**
     * 文件变动
     * @param from 起始目录
     * @param to 目标目录
     * @param fid 文件ID
     */
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