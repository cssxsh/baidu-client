package xyz.cssxsh.baidu.disk.data

/**
 * 错误基本信息
 * @property errorNo 错误码
 * @property errorMessage 有关该错误的描述。
 * @property requestId 发起请求的请求 Id。
 */
public interface NetDiskErrorInfo {
    public val errorNo: Int
    public val errorMessage: String
    public val requestId: String
}