package xyz.cssxsh.baidu.auth

/**
 * [doc](https://openauth.baidu.com/doc/doc.html)
 */
enum class GrantType(val value: String) {

    /**
     * 又称Web Server Flow，适用于所有有Server端配合的应用。
     * 有效期一个月的Access Token+有效期十年的Refresh Token。
     *
     * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/authorization)
     */
    AUTHORIZATION(value = "authorization_code"),

    /**
     * 即采用应用公钥、密钥获取Access Token，适用于任何带server类型应用。
     * 通过此授权方式获取Access Token仅可访问平台授权类的接口。
     * 有效期一个月的Access Token+有效期十年的Refresh Token。
     *
     * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/client)
     */
    CREDENTIALS(value = "client_credentials"),

    /**
     * 适用于一些输入受限的设备上（如只有数码液晶显示屏的打印机、电视机等）。
     * 有效期一个月的Access Token+有效期十年的Refresh Token。
     *
     * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
     */
    DEVICE(value = "device_token"),

    /**
     * 适用于一些输入受限的设备上（如只有数码液晶显示屏的打印机、电视机等）。
     * 有效期一个月的Access Token+有效期十年的Refresh Token。
     *
     * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
     */
    REFRESH(value = "refresh_token");

    override fun toString(): String = value
}