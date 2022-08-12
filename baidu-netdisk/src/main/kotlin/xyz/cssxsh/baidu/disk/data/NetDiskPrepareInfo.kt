package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 文件预上传结果
 * @param uploadId 上传ID
 * @param need 需要上传的分块序列号
 * @param path 文件路径
 * @param type 返回的创建结果
 * @param file 已存在或创建成功时，文件信息
 */
@Serializable
public data class NetDiskPrepareInfo(
    @SerialName("block_list")
    val need: Set<Int> = emptySet(),
    @SerialName("path")
    val path: String? = null,
    @SerialName("return_type")
    val type: PrepareReturnType = PrepareReturnType.NONE,
    @SerialName("uploadid")
    val uploadId: String? = null,
    @SerialName("info")
    val file: NetDiskFileRecord? = null,
    @SerialName("errmsg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo