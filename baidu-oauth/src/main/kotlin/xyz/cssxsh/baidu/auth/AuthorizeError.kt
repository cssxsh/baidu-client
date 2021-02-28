package xyz.cssxsh.baidu.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 * @see AuthorizeErrorType
 * @see AuthorizeException
 */
@Serializable
data class AuthorizeError(
    @SerialName("error")
    val error: AuthorizeErrorType,
    @SerialName("error_description")
    val description: String
)