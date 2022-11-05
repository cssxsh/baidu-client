package xyz.cssxsh.baidu.oauth

/**
 * 百度认证客户端设置
 * @property appName 应用名
 * @property appId 应用ID
 * @property appKey 应用Key
 * @property secretKey 密钥
 */
public interface BaiduAuthConfig {

    public val appName: String

    public val appId: Long

    public val appKey: String

    public val secretKey: String
}