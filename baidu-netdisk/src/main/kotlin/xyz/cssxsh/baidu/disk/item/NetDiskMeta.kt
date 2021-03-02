package xyz.cssxsh.baidu.disk.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.baidu.NumberToBooleanSerializer
import xyz.cssxsh.baidu.disk.CategoryType
import xyz.cssxsh.baidu.disk.NetDiskFileInfo

@Serializable
data class NetDiskMeta(
    @SerialName("app_id")
    val appId: Int,
    @SerialName("block_list")
    val blocks: List<String> = emptyList(),
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("ctime")
    override val created: Long,
    @SerialName("extent_int3")
    private val extentInt3: Int,
    @SerialName("extent_tinyint1")
    private val extentTinyint1: Int,
    @SerialName("extent_tinyint2")
    private val extentTinyint2: Int,
    @SerialName("extent_tinyint3")
    private val extentTinyint3: Int,
    @SerialName("extent_tinyint4")
    private val extentTinyint4: Int,
    @SerialName("file_key")
    val fileKey: String = "",
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("ifhassubdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    val ifHasSubDir: Boolean = false,
    @SerialName("isdelete")
    @Serializable(with = NumberToBooleanSerializer::class)
    val isDelete: Boolean,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("local_ctime")
    val localCreatedTime: Int,
    @SerialName("local_mtime")
    val localModifiedTime: Int,
    @SerialName("md5")
    override val md5: String = "",
    @SerialName("mtime")
    override val modified: Long,
    @SerialName("oper_id")
    val operatorId: Int,
    @SerialName("parent_path")
    val parentPath: String,
    @SerialName("path")
    override val path: String,
    @SerialName("privacy")
    @Serializable(with = NumberToBooleanSerializer::class)
    val privacy: Boolean,
    @SerialName("s3_handle")
    val handle: String? = null,
    @SerialName("server_ctime")
    val serverCreatedTime: Long,
    @SerialName("server_filename")
    override val filename: String,
    @SerialName("server_mtime")
    val serverModifiedTime: Long,
    @SerialName("share")
    private val share: Int,
    @SerialName("size")
    override val size: Long,
    @SerialName("status")
    val status: Int, // XXX
    @SerialName("user_id")
    val userId: Long,
    @SerialName("videotag")
    val videoTag: Int, // XXX
    @SerialName("wpfile")
    private val wpfile: Int
): NetDiskFileInfo