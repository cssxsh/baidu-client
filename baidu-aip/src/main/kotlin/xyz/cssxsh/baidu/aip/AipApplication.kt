package xyz.cssxsh.baidu.aip

import xyz.cssxsh.baidu.*

sealed interface AipApplication {
    val client: AipClient
}