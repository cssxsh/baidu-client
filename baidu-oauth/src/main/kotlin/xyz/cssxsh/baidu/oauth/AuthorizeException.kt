package xyz.cssxsh.baidu.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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
 *
 * @see AuthorizeErrorType
 * @see AuthorizeAccessToken
 */
class AuthorizeException(private val json: AuthorizeError) : IllegalStateException(json.description) {
    constructor(context: String) : this(Json.decodeFromString(AuthorizeError.serializer(), context))

    val type: AuthorizeErrorType by json::error
}