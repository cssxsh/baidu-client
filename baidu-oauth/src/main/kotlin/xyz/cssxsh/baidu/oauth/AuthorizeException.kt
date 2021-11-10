package xyz.cssxsh.baidu.oauth

import kotlinx.serialization.*
import kotlinx.serialization.json.*

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
class AuthorizeException(private val json: AuthorizeError) : IllegalStateException(json.description) {
    constructor(context: String) : this(Json.decodeFromString(AuthorizeError.serializer(), context))

    val type: AuthorizeErrorType by json::error
}