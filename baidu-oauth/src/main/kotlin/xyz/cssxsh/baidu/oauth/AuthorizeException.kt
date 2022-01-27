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
public data class AuthorizeError(
    @SerialName("error")
    val error: AuthorizeErrorType,
    @SerialName("error_description")
    val description: String
)

/**
 * @see AuthorizeErrorType
 * @see AuthorizeAccessToken
 */
public class AuthorizeException(public val data: AuthorizeError, cause: Throwable) : IllegalStateException(data.description, cause) {

    public val type: AuthorizeErrorType get() = data.error
}

/**
 * @see AuthorizeErrorType
 * @see AuthorizeAccessToken
 */
public suspend fun AuthorizeException(cause: ClientRequestException): AuthorizeException {
    return AuthorizeException(cause.response.receive(), cause)
}