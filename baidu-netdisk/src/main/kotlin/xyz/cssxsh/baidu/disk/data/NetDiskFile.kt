package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.OffsetDateTime

@Serializable
public data class NetDiskFile(
    @SerialName("category")
    override val category: CategoryType = CategoryType.NONE,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("oper_id")
    override val operatorId: Long = 0,
    @SerialName("owner_id")
    override val ownerId: Long = 0,
    @SerialName("md5")
    override val md5: String = "",
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
    override val created: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("server_filename")
    override val filename: String = path.substringAfterLast('/'),
    @SerialName("server_mtime")
    @Serializable(TimestampSerializer::class)
    override val modified: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("size")
    override val size: Long = 0,
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean = false,
    @SerialName("dir_empty")
    @Serializable(with = NumberToBooleanSerializer::class)
    val empty: Boolean = true,
    @SerialName("share")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val share: Boolean = false,
    @SerialName("lodocpreview")
    val loDocPreview: String? = null,
    @SerialName("docpreview")
    val docpreview: String? = null,
) : NetDiskFileInfo