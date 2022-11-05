package xyz.cssxsh.baidu.unit

import xyz.cssxsh.baidu.unit.data.*

public class BaiduUnitException(public val request: Any, public val response: BaiduUnitExceptionInfo) :
    IllegalStateException() {
    override val message: String = "${response.errorCode} - ${response.errorMessage}"
}