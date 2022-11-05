package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 查询文件信息 结果
 * @param list 文件元数据
 * @param names 文件和类型
 */
@Serializable
public data class NetDiskQuery(
    @SerialName("list")
    override val list: List<NetDiskFileMeta>,
    @SerialName("names")
    val names: Map<String, String>,
    @SerialName("errmsg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo, NetDiskFileData