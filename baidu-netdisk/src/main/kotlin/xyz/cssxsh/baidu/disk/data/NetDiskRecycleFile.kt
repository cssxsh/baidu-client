package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

/**
 * @param deleted 删除时间
 */
@Serializable
public data class NetDiskRecycleFile(
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
    @SerialName("server_filename")
    override val filename: String = path.substringAfterLast('/'),
    @SerialName("size")
    override val size: Long = 0,
    @SerialName("isdir")
    @Serializable(NumberToBooleanSerializer::class)
    override val isDir: Boolean = false,
    @SerialName("share")
    @Serializable(NumberToBooleanSerializer::class)
    override val share: Boolean = false,
    @SerialName("server_ctime")
    @Serializable(TimestampSerializer::class)
    override val created: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("server_mtime")
    @Serializable(TimestampSerializer::class)
    override val modified: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("leftTime")
    @Serializable(TimestampSerializer::class)
    val deleted: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("local_ctime")
    @Serializable(TimestampSerializer::class)
    val localCreated: OffsetDateTime = OffsetDateTime.MIN,
    @Serializable(TimestampSerializer::class)
    @SerialName("local_mtime")
    val localModified: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("delete_type")
    val deleteType: Int
) : NetDiskFileInfo