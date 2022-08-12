package xyz.cssxsh.baidu.disk.data

/**
 * PCS错误信息 [doc](https://openapi.baidu.com/wiki/index.php?title=docs/pcs/rest/file_data_apis_error)
 * @property errorCode 错误码
 * @property errorMessage 有关该错误的描述。
 * @property requestId 请求ID
 */
public interface PCSErrorInfo {
    public val errorCode: Int
    public val errorMessage: String
    public val requestId: Long
}