package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.OffsetDateTime

@Serializable
public data class NetDiskFileRecord(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("ctime")
    @Serializable(TimestampSerializer::class)
    override val created: OffsetDateTime,
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
    @SerialName("mtime")
    @Serializable(TimestampSerializer::class)
    override val modified: OffsetDateTime,
    @SerialName("path")
    override val path: String,
    @SerialName("size")
    override val size: Long,
    @SerialName("filename")
    override val filename: String = "",
    @SerialName("share")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val share: Boolean = false
) : NetDiskFileInfo