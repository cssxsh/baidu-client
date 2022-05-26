package xyz.cssxsh.baidu.oauth.exception

import io.ktor.client.call.*
import io.ktor.client.features.*
import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.oauth.data.*



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