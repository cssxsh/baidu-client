package xyz.cssxsh.baidu.aip

import xyz.cssxsh.baidu.*

public sealed interface AipApplication {
    public val client: AipClient
}