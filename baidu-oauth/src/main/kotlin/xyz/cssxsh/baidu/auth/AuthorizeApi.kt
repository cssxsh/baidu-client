package xyz.cssxsh.baidu.auth

object AuthorizeApi {
    const val AUTHORIZE = "http://openapi.baidu.com/oauth/2.0/authorize"
    const val TOKEN = "https://openapi.baidu.com/oauth/2.0/token"
    const val DEVICE_CODE = "https://openapi.baidu.com/oauth/2.0/device/code"
    const val DEVICE = "http://openapi.baidu.com/device"
    const val DEFAULT_REDIRECT = "oob"
    const val ACCESS_EXPIRES = 86400L
}