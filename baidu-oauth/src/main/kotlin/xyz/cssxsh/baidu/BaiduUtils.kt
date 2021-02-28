package xyz.cssxsh.baidu

import io.ktor.http.*

/**
 * Unit second
 */
typealias SecondUnit = Long

fun ParametersBuilder.appendParameter(key: String, value: Any?): Unit =
    value?.let { append(key, it.toString()) } ?: Unit

fun Boolean.toInt(): Int = if (this) 1 else 0
