package xyz.cssxsh.baidu.oauth.exception

import io.ktor.client.call.*
import io.ktor.client.plugins.*
import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.oauth.data.*


/**
 * 认证异常
 * @see AuthorizeErrorType
 * @see AuthorizeAccessToken
 */
public class AuthorizeException(public val data: AuthorizeError, cause: Throwable) :
    IllegalStateException(data.description, cause) {

    public val type: AuthorizeErrorType get() = data.error
}

/**
 * 认证异常
 * @see AuthorizeErrorType
 * @see AuthorizeAccessToken
 */
public suspend fun AuthorizeException(cause: ClientRequestException): AuthorizeException {
    return AuthorizeException(cause.response.body(), cause)
}