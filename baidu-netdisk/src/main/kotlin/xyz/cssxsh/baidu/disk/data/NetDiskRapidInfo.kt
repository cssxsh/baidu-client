package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

/**
 * 快速文件上传结果
 * @param file 成功时不为空
 */
@Serializable
public data class NetDiskRapidInfo(
    @SerialName("info")
    val file: NetDiskFileRecord? = null,
    @SerialName("errmsg")
    override val errorMessage: String = "",
    @SerialName("errno")
    override val errorNo: Int = 0,
    @SerialName("request_id")
    override val requestId: String
) : NetDiskErrorInfo