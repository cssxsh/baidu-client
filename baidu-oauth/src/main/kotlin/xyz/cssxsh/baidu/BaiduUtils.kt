package xyz.cssxsh.baidu

import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.http.*
import xyz.cssxsh.baidu.oauth.AuthorizeException

/**
 * Unit second
 */
typealias SecondUnit = Long

fun ParametersBuilder.appendParameter(key: String, value: Any?) {
    value?.let { append(key, it.toString()) }
}

fun Boolean.toInt(): Int = if (this) 1 else 0

suspend fun ClientRequestException.toAuthorizeExceptionOrNull() =
    runCatching { AuthorizeException(response.readText()) }.getOrNull()

