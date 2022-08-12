package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

@Serializable
public data class NetDiskQuery(
    @SerialName("list")
    val list: List<NetDiskFileMeta>,
    @SerialName("names")
    val names: Map<String, String>,
    @SerialName("errmsg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo