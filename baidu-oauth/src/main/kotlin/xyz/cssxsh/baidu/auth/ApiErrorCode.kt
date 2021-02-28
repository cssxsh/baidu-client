package xyz.cssxsh.baidu.auth

/**
 * [wiki](https://openauth.baidu.com/doc/appendix.html)
 */
enum class ApiErrorCode(val code: Int, val message: String) {
    /**
     * 未知错误，如果频繁发生此错误，请联系developer_support@baidu.com
     */
    UNKNOWN(code = 1, message = "Unknown error"),

    /**
     * 服务暂时不可用
     */
    UNAVAILABLE(code = 2, message = "Service temporarily unavailable"),

    /**
     * 访问URL错误，该接口不能访问
     */
    UNSUPPORTED(code = 3, message = "Unsupported openapi method"),

    /**
     * 该APP访问该接口的QPS达到上限
     */
    LIMIT(code = 4, message = "Open api request limit reached"),

    /**
     * 该APP访问该接口超过每天的访问限额
     */
    LIMIT_DAILY(code = 17, message = "Open api daily request limit reached"),

    /**
     * 该APP访问该接口超过QPS限额
     */
    LIMIT_QPS(code = 18, message = "Open api qps request limit reached"),

    /**
     * 该APP访问该接口超过总量限额
     */
    LIMIT_TOTAL(code = 19, message = "Open api total request limit reached"),

    /**
     * 	访问的客户端IP不在白名单内
     */
    UNAUTHORIZED_IP(code = 5, message = "Unauthorized client IP address"),

    /**
     * 该APP没有访问该接口的权限
     */
    PERMISSION_DATA(code = 6, message = "No permission to access data"),

    /**
     * 	没有权限获取用户手机号
     */
    PERMISSION_MOBILE(code = 213, message = "Access token invalid or no longer valid"),

    /**
     * 没有获取到token参数
     */
    INVALID_PARAMETER(code = 100, message = "Invalid parameter"),

    /**
     * token不合法
     */
    TOKEN_INVALID(code = 110, message = "Access token invalid or no longer valid"),

    /**
     * token已过期
     */
    TOKEN_EXPIRED(code = 111, message = "Access token expired");
}