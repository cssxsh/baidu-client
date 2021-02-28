package xyz.cssxsh.baidu.auth

/**
 * [doc](https://openauth.baidu.com/doc/doc.html)
 */
enum class AuthorizeType(val value: String) {

    /**
     * 又称Web Server Flow，适用于所有有Server端配合的应用。
     * 有效期一个月的Access Token+有效期十年的Refresh Token。
     *
     * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/authorization)
     */
    AUTHORIZATION(value = "code"),

    /**
     * 又称User-Agent Flow，适用于所有无Server端配合的应用（桌面客户端需要内嵌浏览器）。
     * 有效期一个月的Access Token。
     *
     * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/implicit)
     */
    IMPLICIT(value = "token");

    override fun toString(): String = value
}