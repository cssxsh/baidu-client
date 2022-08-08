package xyz.cssxsh.baidu.aip


public class BaiduAipException(public val info: AipExceptionInfo) : IllegalStateException() {
    override val message: String = "${info.errorCode} - ${info.errorMessage}"
}