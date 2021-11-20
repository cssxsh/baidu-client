package xyz.cssxsh.baidu.exception

import xyz.cssxsh.baidu.BaiduAuthClient

class NotTokenException(override val message: String?, val client: BaiduAuthClient) : IllegalStateException()