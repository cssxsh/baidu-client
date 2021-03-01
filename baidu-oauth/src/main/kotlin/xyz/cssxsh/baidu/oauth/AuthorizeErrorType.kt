package xyz.cssxsh.baidu.oauth

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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

    companion object Serializer : KSerializer<AuthorizeErrorType> {
        override val descriptor: SerialDescriptor
            get() = buildSerialDescriptor("AuthorizeErrorTypeSerializer", SerialKind.ENUM)

        override fun serialize(encoder: Encoder, value: AuthorizeErrorType) = encoder.encodeString(value.name.toLowerCase())

        override fun deserialize(decoder: Decoder): AuthorizeErrorType = valueOf(decoder.decodeString().toUpperCase())
    }
}