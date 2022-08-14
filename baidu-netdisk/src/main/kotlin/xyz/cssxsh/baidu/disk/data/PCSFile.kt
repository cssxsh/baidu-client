package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

@Serializable
public data class PCSFile(
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("oper_id")
    override val operatorId: Long = 0,
    @SerialName("owner_id")
    override val ownerId: Long = 0,
    @SerialName("app_id")
    val appId: Long,
    @SerialName("user_id")
    val userId: Long,
    @SerialName("category")
    override val category: CategoryType = CategoryType.NONE,
    @SerialName("path")
    override val path: String,
    @SerialName("server_filename")
    override val filename: String,
    @SerialName("size")
    override val size: Long,
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
    override val isDir: Boolean,
    @SerialName("ifhassubdir")
    @Serializable(NumberToBooleanSerializer::class)
    val hasSubDir: Boolean = false,
    @SerialName("share")
    @Serializable(NumberToBooleanSerializer::class)
    override val share: Boolean,
    @SerialName("isdelete")
    @Serializable(NumberToBooleanSerializer::class)
    val isDelete: Boolean = false,
    @SerialName("s3_handle")
    val handle: String? = null
) : NetDiskFileInfo