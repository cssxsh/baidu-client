package xyz.cssxsh.baidu.oauth.data

import kotlinx.serialization.*

/**
 * 认证错误信息数据
 * @see AuthorizeErrorType
 * @see xyz.cssxsh.baidu.oauth.exception.AuthorizeException
 */
@Serializable
public data class AuthorizeError(
    @SerialName("error")
    val error: AuthorizeErrorType,
    @SerialName("error_description")
    val description: String
)