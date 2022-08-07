package xyz.cssxsh.baidu.unit

import xyz.cssxsh.baidu.unit.data.*

public class BaiduUnitException(public val request: RequestBody, public val response: ResponseBody) :
    IllegalStateException() {
    override val message: String = "${response.errorCode} - ${response.errorMessage}"
}