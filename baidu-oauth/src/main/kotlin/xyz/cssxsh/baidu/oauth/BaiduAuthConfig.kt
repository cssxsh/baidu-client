package xyz.cssxsh.baidu.oauth

/**
 * 百度认证客户端设置
 */
public interface BaiduAuthConfig {

    public val appName: String

    public val appId: Long

    public val appKey: String

    public val secretKey: String
}