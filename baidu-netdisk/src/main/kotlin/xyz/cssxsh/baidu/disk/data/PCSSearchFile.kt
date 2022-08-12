package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.OffsetDateTime

@Serializable
public data class PCSSearchFile(
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
    @SerialName("isdir")
    @Serializable(NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("share")
    @Serializable(NumberToBooleanSerializer::class)
    override val share: Boolean = false
) : NetDiskFileInfo {
    @Transient
    override val filename: String = path.substringAfterLast('/')
}