package xyz.cssxsh.baidu.exption

import xyz.cssxsh.baidu.BaiduAuthClient

class NotTokenException(override val message: String?, val client: BaiduAuthClient) : IllegalStateException()