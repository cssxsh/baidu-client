package xyz.cssxsh.baidu.unit

import xyz.cssxsh.baidu.unit.data.*

/**
 * Unit 执行异常
 * @property request 请求内容
 * @property response 返回错误信息
 */
public class BaiduUnitException(public val request: Any, public val response: BaiduUnitExceptionInfo) :
    IllegalStateException() {
    override val message: String = "${response.errorCode} - ${response.errorMessage}"
}