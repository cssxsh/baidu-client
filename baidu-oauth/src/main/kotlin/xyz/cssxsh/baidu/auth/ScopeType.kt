package xyz.cssxsh.baidu.auth

/**
 * [developer-web](https://developer.baidu.com/)
 */
enum class ScopeType(val value: String) {

    /**
     * 用户基本权限，可以获取用户的基本信息 。
     */
    BASIC(value = "basic"),

    /**
     * 往用户的百度首页上发送消息提醒，相关API任何应用都能使用，
     * 但要想将消息提醒在百度首页显示，需要第三方在注册应用时额外填写相关信息。
     */
    SUPER_MESSAGE(value = "super_msg"),

    /**
     * 	获取用户在个人云存储中存放的数据。
     */
    NET_DISK(value = "netdisk"),

    /**
     * 	可以访问公共的开放API。
     */
    PUBLIC(value = "public"),

    /**
     * 可以访问Hao123 提供的开放API接口该权限需要申请开通，请将具体的理由和用途发邮件给tuangou@baidu.com。
     */
    HAO123(value = "hao123"),

    /**
     * TODO
     */
    EMAIL(value = "email"),

    /**
     * TODO
     */
    MOBILE(value = "mobile");

    override fun toString(): String = value
}