package xyz.cssxsh.baidu.oauth

import kotlinx.serialization.json.Json

/**
 *
 * @see AuthorizeErrorType
 * @see AuthorizeAccessToken
 */
class AuthorizeException(private val json: AuthorizeError) : IllegalStateException(json.description) {
    constructor(context: String) : this(Json.decodeFromString(AuthorizeError.serializer(), context))

    val type: AuthorizeErrorType
        get() = json.error
}