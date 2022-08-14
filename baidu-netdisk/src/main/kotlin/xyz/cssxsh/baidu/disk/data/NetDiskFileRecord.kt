package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

@Serializable
public data class NetDiskFileRecord(
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
    override val filename: String = "",
    @SerialName("size")
    override val size: Long,
    @SerialName("isdir")
    @Serializable(NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("share")
    @Serializable(NumberToBooleanSerializer::class)
    override val share: Boolean = false,
    @SerialName("ctime")
    @Serializable(TimestampSerializer::class)
    override val created: OffsetDateTime,
    @SerialName("mtime")
    @Serializable(TimestampSerializer::class)
    override val modified: OffsetDateTime
) : NetDiskFileInfo