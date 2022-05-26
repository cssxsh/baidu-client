package xyz.cssxsh.baidu.oauth

import java.time.*

public interface BaiduAuthStatus {

    public var expires: OffsetDateTime

    public var accessTokenValue: String

    public var refreshTokenValue: String

    public var scope: List<String>

    public fun save(token: AuthorizeAccessToken) {
        accessTokenValue = token.accessToken
        refreshTokenValue = token.refreshToken
        expires = token.expires
        scope = token.scope
    }
}