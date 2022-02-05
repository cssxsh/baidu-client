package xyz.cssxsh.baidu.aip

public interface AipExceptionInfo {
    public val logId: Long? get() = null
    public val errorCode: Long?
    public val errorMessage: String?
}