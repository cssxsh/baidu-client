@file:OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)

package xyz.cssxsh.baidu.oauth

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import xyz.cssxsh.baidu.*

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

/**
 *
 * @see xyz.cssxsh.baidu.oauth.AuthorizeError
 * @see xyz.cssxsh.baidu.oauth.AuthorizeException
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/error)
 */
@Serializable(with = AuthorizeErrorType.Serializer::class)
enum class AuthorizeErrorType(val message: String) {
    /**
     * 请求缺少某个必需参数，包含一个不支持的参数或参数值，或者格式不正确。
     */
    INVALID_REQUEST(message = "invalid refresh token"),

    /**
     * 	client_id”、“client_secret”参数无效。
     */
    INVALID_CLIENT(message = "unknown client id"),

    /**
     * 提供的Access Grant是无效的、过期的或已撤销的，
     * 例如，Authorization Code无效(一个授权码只能使用一次)、
     * Refresh Token无效、
     * redirect_uri与获取Authorization Code时提供的不一致、
     * Device Code无效(一个设备授权码只能使用一次)等。
     */
    INVALID_GRANT(message = "The provided authorization grant is revoked"),

    /**
     * 应用没有被授权，无法使用所指定的grant_type。
     */
    UNAUTHORIZED_CLIENT(message = "The client is not authorized to use this authorization grant type"),

    /**
     * “grant_type”百度OAuth2.0服务不支持该参数。
     */
    UNSUPPORTED_GRANT_TYPE(message = "The authorization grant type is not supported"),

    /**
     * 请求的“scope”参数是无效的、未知的、格式不正确的、或所请求的权限范围超过了数据拥有者所授予的权限范围。
     */
    INVALID_SCOPE(message = "The requested scope is exceeds the scope granted by the resource owner"),

    /**
     * 提供的Refresh Token已过期
     */
    EXPIRED_TOKEN(message = "refresh token has been used"),

    /**
     * 	“redirect_uri”所在的根域与开发者注册应用时所填写的根域名不匹配。
     */
    REDIRECT_URI_MISMATCH(message = "Invalid redirect uri"),

    /**
     * “response_type”参数值不为百度OAuth2.0服务所支持，或者应用已经主动禁用了对应的授权模式
     */
    UNSUPPORTED_RESPONSE_TYPE(message = "The response type is not supported"),

    /**
     * Device Flow中，设备通过Device Code换取Access Token的接口过于频繁，两次尝试的间隔应大于5秒。
     */
    SLOW_DOWN(message = "The device is polling too frequently"),

    /**
     * Device Flow中，用户还没有对Device Code完成授权操作。
     */
    AUTHORIZATION_PENDING(message = "User has not yet completed the authorization"),

    /**
     * 	Device Flow中，用户拒绝了对Device Code的授权操作。
     */
    AUTHORIZATION_DECLINED(message = "User has declined the authorization"),

    /**
     * 	Implicit Grant模式中，浏览器请求的Referer与根域名绑定不匹配
     */
    INVALID_REFERER(message = "Invalid Referer");

    companion object Serializer : KSerializer<AuthorizeErrorType> by LowerCaseSerializer()
}

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

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/authorization)
 */
enum class DisplayType(val value: String) {
    /**
     * 全屏形式的授权页面(默认)，适用于web应用。
     */
    PAGE(value = "page"),

    /**
     * 弹框形式的授权页面，适用于桌面软件应用和web应用。
     */
    POPUP(value = "popup"),

    /**
     * 浮层形式的授权页面，只能用于站内web应用。
     */
    DIALOG(value = "dialog"),

    /**
     * Iphone/Android等智能移动终端上用的授权页面，适用于Iphone/Android等智能移动终端上的应用。
     */
    MOBILE(value = "mobile"),

    /**
     * 电视等超大显示屏使用的授权页面
     */
    TV(value = "tv"),

    /**
     * IPad/Android等智能平板电脑使用的授权页面。
     */
    PAD(value = "pad");

    override fun toString(): String = value
}

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
    CLIENT_CREDENTIALS(value = "client_credentials"),

    /**
     * [CLIENT_CREDENTIALS]
     */
    DEVELOPER_CREDENTIALS(value = "developer_credentials"),

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

/**
 * [developer-web](https://developer.baidu.com/)
 */
@Serializable(ScopeTypeSerializer::class)
data class ScopeType(val value: String) {

    companion object {

        /**
         * 用户基本权限，可以获取用户的基本信息 。
         */
        val BASIC = ScopeType(value = "basic")

        /**
         * 往用户的百度首页上发送消息提醒，相关API任何应用都能使用，
         * 但要想将消息提醒在百度首页显示，需要第三方在注册应用时额外填写相关信息。
         */
        val SUPER_MESSAGE = ScopeType(value = "super_msg")

        /**
         * 	获取用户在个人云存储中存放的数据。
         */
        val NET_DISK = ScopeType(value = "netdisk")

        /**
         * 	可以访问公共的开放API。
         */
        val PUBLIC = ScopeType(value = "public")

        /**
         * 可以访问Hao123 提供的开放API接口该权限需要申请开通，请将具体的理由和用途发邮件给tuangou@baidu.com。
         */
        val HAO123 = ScopeType(value = "hao123")

        /**
         * XXX
         */
        val EMAIL = ScopeType(value = "email")

        /**
         * XXX
         */
        val MOBILE = ScopeType(value = "mobile")
    }

    override fun toString(): String = value
}

object ScopeTypeSerializer : KSerializer<ScopeType> {
    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("ScopeTypeSerializer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ScopeType = ScopeType(value = decoder.decodeString())

    override fun serialize(encoder: Encoder, value: ScopeType) = encoder.encodeString(value.value)

}

object ScopesSerializer : KSerializer<List<ScopeType>> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("ScopesSerializer", PrimitiveKind.STRING)

    private val SPLIT_REGEX = """([,]|\s|[+])""".toRegex()

    fun splitScope(text: String) = text.split(SPLIT_REGEX).map(::ScopeType)

    override fun deserialize(decoder: Decoder): List<ScopeType> = splitScope(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: List<ScopeType>) = encoder.encodeString(value.joinToString(" "))
}