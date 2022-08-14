package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import xyz.cssxsh.baidu.api.*
import java.time.*

@Serializable
public data class NetDiskCreateFile(
    @SerialName("category")
    override val category: CategoryType = CategoryType.NONE,
    @SerialName("fs_id")
    override val id: Long = 0,
    @SerialName("oper_id")
    override val operatorId: Long = 0,
    @SerialName("owner_id")
    override val ownerId: Long = 0,
    @SerialName("md5")
    override val md5: String = "",
    @SerialName("path")
    override val path: String = "",
    @SerialName("server_filename")
    override val filename: String = "",
    @SerialName("size")
    override val size: Long = 0,
    @SerialName("isdir")
    @Serializable(NumberToBooleanSerializer::class)
    override val isDir: Boolean = false,
    @Serializable(NumberToBooleanSerializer::class)
    override val share: Boolean = false,
    @SerialName("ctime")
    @Serializable(TimestampSerializer::class)
    override val created: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("mtime")
    @Serializable(TimestampSerializer::class)
    override val modified: OffsetDateTime = OffsetDateTime.MIN,
    @SerialName("name")
    val name: String = "",
    @SerialName("status")
    val status: Int? = null,
    @SerialName("errmsg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String = ""
) : NetDiskErrorInfo, NetDiskFileInfo