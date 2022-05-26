package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import xyz.cssxsh.baidu.api.*

@Serializable
public data class NetDiskList<NetDiskFileInfo>(
    @SerialName("errno")
    val errno: Int,
    @SerialName("guid")
    val guid: Int,
    @SerialName("guid_info")
    val guidInfo: String,
    @SerialName("info")
    val info: List<NetDiskFileInfo>,
    @SerialName("contentlist")
    internal val contentList: JsonArray? = null,
    @SerialName("request_id")
    val requestId: RequestIdType,
    @SerialName("has_more")
    @Serializable(with = NumberToBooleanSerializer::class)
    val hasMore: Boolean = false
)

@Serializable
public data class NetDiskBt(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("local_ctime")
    val localCreatedTime: Long,
    @SerialName("local_mtime")
    val localModifiedTime: Long,
    @SerialName("md5")
    override val md5: String,
    @SerialName("object_key")
    internal val objectKey: String,
    @SerialName("path")
    override val path: String,
    @SerialName("server_ctime")
    override val created: Long,
    @SerialName("server_filename")
    override val filename: String,
    @SerialName("server_mtime")
    override val modified: Long,
    @SerialName("share")
    internal val share: Int,
    @SerialName("size")
    override val size: Long,
    @SerialName("wpfile")
    internal val wpfile: Int,
) : NetDiskFileInfo

@Serializable
public data class NetDiskDoc(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("docpreview")
    val docPreview: String? = null,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("local_ctime")
    val localCreatedTime: Long,
    @SerialName("local_mtime")
    val localModifiedTime: Long,
    @SerialName("lodocpreview")
    val loDocPreview: String? = null,
    @SerialName("md5")
    override val md5: String,
    @SerialName("object_key")
    internal val objectKey: String,
    @SerialName("path")
    override val path: String,
    @SerialName("server_ctime")
    override val created: Long,
    @SerialName("server_filename")
    override val filename: String,
    @SerialName("server_mtime")
    override val modified: Long,
    @SerialName("share")
    val share: Int,
    @SerialName("size")
    override val size: Long,
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("wpfile")
    internal val wpfile: Int,
) : NetDiskFileInfo

@Serializable
public data class NetDiskMeta(
    @SerialName("app_id")
    val appId: Int,
    @SerialName("block_list")
    val blocks: List<String> = emptyList(),
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("ctime")
    override val created: Long,
    @SerialName("extent_int3")
    internal val extentInt3: Int,
    @SerialName("extent_tinyint1")
    internal val extentTinyint1: Int,
    @SerialName("extent_tinyint2")
    internal val extentTinyint2: Int,
    @SerialName("extent_tinyint3")
    internal val extentTinyint3: Int,
    @SerialName("extent_tinyint4")
    internal val extentTinyint4: Int,
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
    internal val share: Int,
    @SerialName("size")
    override val size: Long,
    @SerialName("status")
    val status: Int, // XXX
    @SerialName("user_id")
    val userId: Long,
    @SerialName("videotag")
    val videoTag: Int, // XXX
    @SerialName("wpfile")
    internal val wpfile: Int
) : NetDiskFileInfo

@Serializable
public data class NetDiskVideo(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("local_ctime")
    val localCreatedTime: Long,
    @SerialName("local_mtime")
    val localModifiedTime: Long,
    @SerialName("md5")
    override val md5: String,
    @SerialName("object_key")
    internal val objectKey: String,
    @SerialName("path")
    override val path: String,
    @SerialName("server_ctime")
    override val created: Long,
    @SerialName("server_filename")
    override val filename: String,
    @SerialName("server_mtime")
    override val modified: Long,
    @SerialName("share")
    val share: Int,
    @SerialName("size")
    override val size: Long,
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("wpfile")
    internal val wpfile: Int,
) : NetDiskFileInfo

@Serializable
public data class NetDiskPreCreateFile(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("ctime")
    override val created: Long,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("md5")
    override val md5: String,
    @SerialName("mtime")
    override val modified: Long,
    @SerialName("path")
    override val path: String,
    @SerialName("request_id")
    val requestId: JsonPrimitive? = null,
    @SerialName("size")
    override val size: Long,
    @SerialName("filename")
    override val filename: String = ""
) : NetDiskFileInfo

@Serializable
public data class NetDiskFile(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("md5")
    override val md5: String,
    @SerialName("path")
    override val path: String,
    @SerialName("server_ctime")
    override val created: Long,
    @SerialName("server_filename")
    override val filename: String,
    @SerialName("server_mtime")
    override val modified: Long,
    @SerialName("size")
    override val size: Long,
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean = false,
    @SerialName("local_ctime")
    val localCreatedTime: Long = 0,
    @SerialName("local_mtime")
    val localModifiedTime: Long = 0,
) : NetDiskFileInfo

@Serializable
public data class NetDiskDetailList(
    @SerialName("errmsg")
    val message: String,
    @SerialName("errno")
    val errno: Int,
    @SerialName("list")
    val list: List<NetDiskDetail>,
    @SerialName("names")
    val names: Map<String, String>,
    @SerialName("request_id")
    val requestId: RequestIdType,
    @SerialName("has_more")
    @Serializable(with = NumberToBooleanSerializer::class)
    val hasMore: Boolean = false
)

@Serializable
public data class NetDiskDetail(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("date_taken")
    val date: Long? = null,
    @SerialName("dlink")
    val link: String? = null,
    @SerialName("filename")
    override val filename: String,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("height")
    val height: Int? = null,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("md5")
    override val md5: String,
    @SerialName("oper_id")
    val operatorId: Long,
    @SerialName("path")
    override val path: String,
    @SerialName("server_ctime")
    override val created: Long,
    @SerialName("server_mtime")
    override val modified: Long,
    @SerialName("size")
    override val size: Long,
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("width")
    val width: Int? = null,
) : NetDiskFileInfo

@Serializable
public data class NetDiskFileOrDir(
    @SerialName("category")
    override val category: CategoryType,
    @SerialName("dir_empty")
    @Serializable(with = NumberToBooleanSerializer::class)
    val dirEmpty: Boolean? = null,
    @SerialName("empty")
    val empty: Int? = null,
    @SerialName("fs_id")
    override val id: Long,
    @SerialName("isdir")
    @Serializable(with = NumberToBooleanSerializer::class)
    override val isDir: Boolean,
    @SerialName("local_ctime")
    val localCreatedTime: Long,
    @SerialName("local_mtime")
    val localModifiedTime: Long,
    @SerialName("md5")
    override val md5: String? = null,
    @SerialName("oper_id")
    val operatorId: Int,
    @SerialName("path")
    override val path: String,
    @SerialName("pl")
    internal val pl: Int? = null,
    @SerialName("server_atime")
    val accessed: Long? = null,
    @SerialName("server_ctime")
    override val created: Long,
    @SerialName("server_filename")
    override val filename: String,
    @SerialName("server_mtime")
    override val modified: Long,
    @SerialName("share")
    internal val share: Int? = null,
    @SerialName("size")
    override val size: Long,
    @SerialName("thumbs")
    val thumbs: Map<String, String>? = null,
    @SerialName("unlist")
    internal val unlist: Int? = null,
    @SerialName("wpfile")
    internal val wpfile: Int? = null,
    @SerialName("extent_tinyint1")
    internal val extentTinyint1: Int? = null
) : NetDiskFileInfo

@Serializable
public data class NetDiskCategory(
    @SerialName("count")
    val count: Long,
    @SerialName("real_server_mtime_2")
    internal val realServerMtime2: Long,
    @SerialName("size")
    val size: Long,
    @SerialName("total")
    val total: Long
)