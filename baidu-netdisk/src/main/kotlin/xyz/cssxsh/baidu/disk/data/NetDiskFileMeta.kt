package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

/**
 * 文件元数据
 * @param dlink 下载链接
 */
@Serializable
public data class NetDiskFileMeta(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("oper_id")
    override val operatorId: Long = 0,
    @SerialName("owner_id")
    override val ownerId: Long = 0,
    @SerialName("md5")
    override val md5: String,
    @SerialName("path")
    override val path: String,
    @SerialName("filename")
    override val filename: String,
    @SerialName("size")
    override val size: Long,
    @SerialName("isdir")
    @Serializable(NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("share")
    @Serializable(NumberToBooleanSerializer::class)
    override val share: Boolean = false,
    @SerialName("server_ctime")
    @Serializable(TimestampSerializer::class)
    override val created: OffsetDateTime,
    @SerialName("server_mtime")
    @Serializable(TimestampSerializer::class)
    override val modified: OffsetDateTime,
    @SerialName("server_atime")
    @Serializable(TimestampSerializer::class)
    val accessed: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("local_ctime")
    @Serializable(TimestampSerializer::class)
    val localCreated: OffsetDateTime = OffsetDateTime.MIN,
    @Serializable(TimestampSerializer::class)
    @SerialName("local_mtime")
    val localModified: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("dlink")
    val dlink: String = ""
) : NetDiskFileInfo