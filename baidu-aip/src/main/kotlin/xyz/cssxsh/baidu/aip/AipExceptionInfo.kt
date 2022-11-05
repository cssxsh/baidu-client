package xyz.cssxsh.baidu.aip

/**
 * AIP 异常信息
 */
public interface AipExceptionInfo {
    public val logId: Long? get() = null
    public val errorCode: Long?
    public val errorMessage: String?
}