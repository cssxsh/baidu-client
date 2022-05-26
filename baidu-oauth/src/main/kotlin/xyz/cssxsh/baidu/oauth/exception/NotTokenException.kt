package xyz.cssxsh.baidu.oauth.exception

import xyz.cssxsh.baidu.oauth.*

public class NotTokenException(override val message: String?, public val client: BaiduAuthClient<*>) :
    IllegalStateException()