package xyz.cssxsh.baidu.oauth.data

import kotlinx.serialization.*
import kotlinx.serialization.builtins.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import xyz.cssxsh.baidu.api.*

/**
 * [wiki](https://openauth.baidu.com/doc/appendix.html)
 * @property UNKNOWN 未知错误，如果频繁发生此错误，请联系developer_support@baidu.com
 * @property UNAVAILABLE 服务暂时不可用
 * @property UNSUPPORTED 访问URL错误，该接口不能访问
 * @property LIMIT 该APP访问该接口的QPS达到上限
 * @property LIMIT_DAILY 该APP访问该接口超过每天的访问限额
 * @property LIMIT_QPS 该APP访问该接口超过QPS限额
 * @property LIMIT_TOTAL 该APP访问该接口超过总量限额
 * @property UNAUTHORIZED_IP 访问的客户端IP不在白名单内
 * @property PERMISSION_DATA 该APP没有访问该接口的权限
 * @property PERMISSION_MOBILE 没有权限获取用户手机号
 * @property INVALID_PARAMETER 没有获取到token参数
 * @property TOKEN_INVALID token不合法
 * @property TOKEN_EXPIRED token已过期
 */
public enum class ApiErrorCode(public val code: Int, public val message: String) {
    UNKNOWN(code = 1, message = "Unknown error"),
    UNAVAILABLE(code = 2, message = "Service temporarily unavailable"),
    UNSUPPORTED(code = 3, message = "Unsupported openapi method"),
    LIMIT(code = 4, message = "Open api request limit reached"),
    LIMIT_DAILY(code = 17, message = "Open api daily request limit reached"),
    LIMIT_QPS(code = 18, message = "Open api qps request limit reached"),
    LIMIT_TOTAL(code = 19, message = "Open api total request limit reached"),
    UNAUTHORIZED_IP(code = 5, message = "Unauthorized client IP address"),
    PERMISSION_DATA(code = 6, message = "No permission to access data"),
    PERMISSION_MOBILE(code = 213, message = "Access token invalid or no longer valid"),
    INVALID_PARAMETER(code = 100, message = "Invalid parameter"),
    TOKEN_INVALID(code = 110, message = "Access token invalid or no longer valid"),
    TOKEN_EXPIRED(code = 111, message = "Access token expired");
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/error)
 * @property INVALID_REQUEST 请求缺少某个必需参数，包含一个不支持的参数或参数值，或者格式不正确。
 * @property INVALID_CLIENT `client_id`、`client_secret` 参数无效。
 * @property INVALID_GRANT 提供的Access Grant是无效的、过期的或已撤销的，例如：
 * * Authorization Code无效(一个授权码只能使用一次)、
 * * Refresh Token无效、
 * * redirect_uri与获取Authorization Code时提供的不一致、
 * * Device Code无效(一个设备授权码只能使用一次)等。
 * @property UNAUTHORIZED_CLIENT 应用没有被授权，无法使用所指定的 `grant_type`。
 * @property UNSUPPORTED_GRANT_TYPE `grant_type` 百度OAuth2.0服务不支持该参数。
 * @property INVALID_SCOPE 请求的 `scope` 参数是无效的、未知的、格式不正确的、或所请求的权限范围超过了数据拥有者所授予的权限范围。
 * @property EXPIRED_TOKEN 提供的Refresh Token已过期
 * @property REDIRECT_URI_MISMATCH `redirect_uri` 所在的根域与开发者注册应用时所填写的根域名不匹配。
 * @property UNSUPPORTED_RESPONSE_TYPE `response_type` 参数值不为百度OAuth2.0服务所支持，或者应用已经主动禁用了对应的授权模式
 * @property SLOW_DOWN Device Flow中，设备通过Device Code换取Access Token的接口过于频繁，两次尝试的间隔应大于5秒。
 * @property AUTHORIZATION_PENDING Device Flow中，用户还没有对Device Code完成授权操作。
 * @property AUTHORIZATION_DECLINED Device Flow中，用户拒绝了对Device Code的授权操作。
 * @property INVALID_REFERER Implicit Grant模式中，浏览器请求的Referer与根域名绑定不匹配
 * @see xyz.cssxsh.baidu.oauth.data.AuthorizeError
 * @see xyz.cssxsh.baidu.oauth.exception.AuthorizeException
 */
@Serializable(with = AuthorizeErrorType.Serializer::class)
public enum class AuthorizeErrorType(public val message: String) {
    INVALID_REQUEST(message = "invalid refresh token"),
    INVALID_CLIENT(message = "unknown client id"),
    INVALID_GRANT(message = "The provided authorization grant is revoked"),
    UNAUTHORIZED_CLIENT(message = "The client is not authorized to use this authorization grant type"),
    UNSUPPORTED_GRANT_TYPE(message = "The authorization grant type is not supported"),
    INVALID_SCOPE(message = "The requested scope is exceeds the scope granted by the resource owner"),
    EXPIRED_TOKEN(message = "refresh token has been used"),
    REDIRECT_URI_MISMATCH(message = "Invalid redirect uri"),
    UNSUPPORTED_RESPONSE_TYPE(message = "The response type is not supported"),
    SLOW_DOWN(message = "The device is polling too frequently"),
    AUTHORIZATION_PENDING(message = "User has not yet completed the authorization"),
    AUTHORIZATION_DECLINED(message = "User has declined the authorization"),
    INVALID_REFERER(message = "Invalid Referer");

    internal companion object Serializer : KSerializer<AuthorizeErrorType> by LowerCaseSerializer()
}

/**
 * [doc](https://openauth.baidu.com/doc/doc.html)
 * @property AUTHORIZATION 又称Web Server Flow，适用于所有有Server端配合的应用。
 * 有效期一个月的Access Token+有效期十年的Refresh Token。
 *
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/authorization)
 * @property IMPLICIT 又称User-Agent Flow，适用于所有无Server端配合的应用（桌面客户端需要内嵌浏览器）。
 * 有效期一个月的Access Token。
 *
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/implicit)
 */
public enum class AuthorizeType(public val value: String) {
    AUTHORIZATION(value = "code"),
    IMPLICIT(value = "token");

    override fun toString(): String = value
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/authorization)
 * @property PAGE 全屏形式的授权页面(默认)，适用于web应用。
 * @property POPUP 弹框形式的授权页面，适用于桌面软件应用和web应用。
 * @property DIALOG 浮层形式的授权页面，只能用于站内web应用。
 * @property MOBILE Iphone/Android等智能移动终端上用的授权页面，适用于Iphone/Android等智能移动终端上的应用。
 * @property TV 电视等超大显示屏使用的授权页面
 * @property PAD IPad/Android等智能平板电脑使用的授权页面。
 */
public enum class DisplayType(public val value: String) {
    PAGE(value = "page"),
    POPUP(value = "popup"),
    DIALOG(value = "dialog"),
    MOBILE(value = "mobile"),
    TV(value = "tv"),
    PAD(value = "pad");

    override fun toString(): String = value
}

/**
 * [doc](https://openauth.baidu.com/doc/doc.html)
 * @property AUTHORIZATION 又称Web Server Flow，适用于所有有Server端配合的应用。
 * 有效期一个月的Access Token+有效期十年的Refresh Token。
 *
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/authorization)
 * @property CLIENT_CREDENTIALS 即采用应用公钥、密钥获取Access Token，适用于任何带server类型应用。
 * 通过此授权方式获取Access Token仅可访问平台授权类的接口。
 * 有效期一个月的Access Token+有效期十年的Refresh Token。
 *
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/client)
 * @property DEVELOPER_CREDENTIALS [CLIENT_CREDENTIALS]
 * @property DEVICE 适用于一些输入受限的设备上（如只有数码液晶显示屏的打印机、电视机等）。
 * 有效期一个月的Access Token+有效期十年的Refresh Token。
 *
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 * @property REFRESH 适用于一些输入受限的设备上（如只有数码液晶显示屏的打印机、电视机等）。
 * 有效期一个月的Access Token+有效期十年的Refresh Token。
 *
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth/device)
 */
public enum class GrantType(public val value: String) {
    AUTHORIZATION(value = "authorization_code"),
    CLIENT_CREDENTIALS(value = "client_credentials"),
    DEVELOPER_CREDENTIALS(value = "developer_credentials"),
    DEVICE(value = "device_token"),
    REFRESH(value = "refresh_token");

    override fun toString(): String = value
}

/**
 * [wiki](http://developer.baidu.com/wiki/index.php?title=docs/oauth)
 * @property SMS 授权页面会默认使用短信动态密码注册登陆方式。
 */
public enum class LoginType(public val value: String) {
    SMS(value = "sms");

    override fun toString(): String = value
}

/**
 * [developer-web](https://developer.baidu.com/)
 * @property BASIC 用户基本权限，可以获取用户的基本信息 。
 * @property SUPER_MESSAGE 往用户的百度首页上发送消息提醒，相关API任何应用都能使用，
 * 但要想将消息提醒在百度首页显示，需要第三方在注册应用时额外填写相关信息。
 * @property NET_DISK 获取用户在个人云存储中存放的数据。
 * @property PUBLIC 可以访问公共的开放API。
 * @property HAO123 可以访问Hao123 提供的开放API接口该权限需要申请开通，请将具体的理由和用途发邮件给tuangou@baidu.com。
 * @property EMAIL 获取邮箱
 * @property MOBILE 获取手机
 * @property WISE_ADAPT 可以访问AIP相关内容
 */
public object ScopeType : KSerializer<List<String>> {
    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = listSerialDescriptor(String.serializer().descriptor)

    private val SPLIT_REGEX = """([,]|\s|[+])""".toRegex()

    /**
     * 解析权限
     */
    public operator fun invoke(text: String): List<String> = text.split(SPLIT_REGEX)

    override fun deserialize(decoder: Decoder): List<String> {
        return invoke(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: List<String>) {
        encoder.encodeString(value.joinToString(" "))
    }

    public const val BASIC: String = "basic"

    public const val SUPER_MESSAGE: String = "super_msg"

    public const val NET_DISK: String = "netdisk"

    public const val PUBLIC: String = "public"

    public const val HAO123: String = "hao123"

    public const val EMAIL: String = "email"

    public const val MOBILE: String = "mobile"

    public const val WISE_ADAPT: String = "wise_adapt"
}