package xyz.cssxsh.baidu.aip

/**
 * AIP 异常信息
 * @property logId 日志ID
 * @property errorCode 错误码
 * @property errorMessage 错误信息
 */
public interface AipExceptionInfo {
    public val logId: Long? get() = null
    public val errorCode: Long?
    public val errorMessage: String?
}