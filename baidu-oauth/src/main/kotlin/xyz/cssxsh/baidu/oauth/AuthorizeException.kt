package xyz.cssxsh.baidu.oauth

import io.ktor.client.call.*
import io.ktor.client.features.*
import kotlinx.serialization.*

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

/**
 * @see AuthorizeErrorType
 * @see AuthorizeAccessToken
 */
class AuthorizeException(val data: AuthorizeError, cause: Throwable) : IllegalStateException(data.description, cause) {

    val type: AuthorizeErrorType by data::error
}

suspend fun AuthorizeException(cause: ClientRequestException) = AuthorizeException(cause.response.receive(), cause)