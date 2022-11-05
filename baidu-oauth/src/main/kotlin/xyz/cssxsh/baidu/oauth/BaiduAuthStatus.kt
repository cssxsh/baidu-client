package xyz.cssxsh.baidu.oauth

import java.time.*

/**
 * 百度认证状态信息接口
 * @property expires 过期时间
 * @property accessTokenValue access token 热值
 * @property refreshTokenValue refresh token 热值
 * @property scope 权限
 */
public interface BaiduAuthStatus {

    public var expires: OffsetDateTime

    public var accessTokenValue: String

    public var refreshTokenValue: String

    public var scope: List<String>

    /**
     * 保存 access token
     */
    public fun save(token: AuthorizeAccessToken) {
        accessTokenValue = token.accessToken
        refreshTokenValue = token.refreshToken
        expires = token.expires
        scope = token.scope
    }
}