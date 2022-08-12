package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.OffsetDateTime

/**
 * @param dlink 下载链接
 */
@Serializable
public data class NetDiskFileMeta(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("dlink")
    val dlink: String = "",
    @SerialName("filename")
    override val filename: String,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("oper_id")
    override val operatorId: Long = 0,
    @SerialName("owner_id")
    override val ownerId: Long = 0,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("md5")
    override val md5: String,
    @SerialName("path")
    override val path: String,
    @SerialName("local_ctime")
    @Serializable(TimestampSerializer::class)
    val localCreated: OffsetDateTime = OffsetDateTime.MIN,
    @Serializable(TimestampSerializer::class)
    @SerialName("local_mtime")
    val localModified: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("server_atime")
    @Serializable(TimestampSerializer::class)
    val accessed: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("server_ctime")
    @Serializable(TimestampSerializer::class)
    override val created: OffsetDateTime,
    @SerialName("server_mtime")
    @Serializable(TimestampSerializer::class)
    override val modified: OffsetDateTime,
    @SerialName("size")
    override val size: Long,
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("share")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val share: Boolean = false
) : NetDiskFileInfo