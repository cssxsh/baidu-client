package xyz.cssxsh.baidu.exception

import xyz.cssxsh.baidu.BaiduAuthClient

public class NotTokenException(override val message: String?, public val client: BaiduAuthClient) :
    IllegalStateException()