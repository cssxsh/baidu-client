package xyz.cssxsh.baidu.auth


/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 */
enum class LoginType(val value: String) {
    /**
     * 授权页面会默认使用短信动态密码注册登陆方式。
     */
    SMS(value = "sms");

    override fun toString(): String = value
}