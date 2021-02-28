package xyz.cssxsh.baidu.auth

/**
 *
 * @see AuthorizeErrorType
 * @see AuthorizeAccessToken
 */
class AuthorizeException(private val json: AuthorizeError) : IllegalStateException(json.description) {

    val type: AuthorizeErrorType
        get() = json.error
}