package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

/**
 * 异步任务信息
 * @param status 任务状态
 * @param list 任务结果
 * @param taskErrorNo 任务错误号
 * @param total 总计
 * @param progress 进度 0~100
 */
@Serializable
public data class NetDiskTask(
    @SerialName("list")
    val list: List<Item> = emptyList(),
    @SerialName("status")
    val status: TaskStatus = TaskStatus.FAILURE,
    @SerialName("task_errno")
    val taskErrorNo: Int = 0,
    @SerialName("total")
    val total: Int = 0,
    @SerialName("progress")
    val progress: Int = 100,
    @SerialName("show_msg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String = ""
) : NetDiskErrorInfo {

    /**
     * 任务内容
     * @param revision 迭代
     * @param isDir 是否文件夹
     * @param path 文件路径
     * @param modified 修改时间
     * @param size 大小
     */
    @Serializable
    public data class Item(
        @SerialName("base_revision")
        val revision: Boolean,
        @SerialName("isdir")
        val isDir: Boolean,
        @SerialName("path")
        val path: String,
        @SerialName("server_mtime")
        @Serializable(TimestampSerializer::class)
        val modified: OffsetDateTime,
        @SerialName("size")
        val size: Long
    )
}