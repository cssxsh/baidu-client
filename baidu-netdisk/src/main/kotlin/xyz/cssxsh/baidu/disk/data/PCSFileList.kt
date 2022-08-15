package xyz.cssxsh.baidu.disk.data

import kotlinx.serialization.*

@Serializable
public data class PCSFileList<F : NetDiskFileInfo>(
    @SerialName("list")
    override val list: List<F>,
    @SerialName("error_code")
    override val errorCode: Int = 0,
    @SerialName("error_msg")
    override val errorMessage: String = "",
    @SerialName("request_id")
    override val requestId: Long
) : PCSErrorInfo, NetDiskFileData