package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.OffsetDateTime

/**
 * 文件上传/创建结果
 * @param id 文件ID
 * @param path 文件路径
 * @param size 文件大小
 * @param md5 文件md5
 * @param created 创建时间
 * @param modified 修改时间
 */
@Serializable
public data class PCSUploadFile(
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("oper_id")
    override val operatorId: Long = 0,
    @SerialName("owner_id")
    override val ownerId: Long = 0,
    @SerialName("category")
    override val category: CategoryType = CategoryType.NONE,
    @SerialName("path")
    override val path: String,
    @SerialName("size")
    override val size: Long = 0,
    @SerialName("md5")
    override val md5: String = "",
    @SerialName("ctime")
    @Serializable(TimestampSerializer::class)
    override val created: OffsetDateTime,
    @SerialName("mtime")
    @Serializable(TimestampSerializer::class)
    override val modified: OffsetDateTime,
    @SerialName("block_list")
    val blocks: List<String> = emptyList(),
    @SerialName("isdir")
    @Serializable(NumberToBooleanSerializer::class)
    override val isDir: Boolean = false,
    @SerialName("error_code")
    override val errorCode: Int = 0,
    @SerialName("error_msg")
    override val errorMessage: String = "",
    @SerialName("request_id")
    override val requestId: Long
) : PCSErrorInfo, NetDiskFileInfo {

    override val share: Boolean get() = false

    @Transient
    override val filename: String = path.substringAfterLast('/')
}