package xyz.cssxsh.baidu.oauth.exception

import xyz.cssxsh.baidu.oauth.*

/**
 * 不能获取 Token 异常
 */
public class NotTokenException(message: String?, public val client: BaiduAuthClient<*>) : IllegalStateException(message)