package xyz.cssxsh.baidu.aip

/**
 * AIP 客户端 API异常
 */
public class BaiduAipException(public val info: AipExceptionInfo) : IllegalStateException() {
    override val message: String = "${info.errorCode} - ${info.errorMessage}"
}