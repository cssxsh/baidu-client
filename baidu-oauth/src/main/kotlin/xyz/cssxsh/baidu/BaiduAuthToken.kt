package xyz.cssxsh.baidu

import java.time.*

public interface BaiduAuthToken {

    public val expires: OffsetDateTime

    public val accessTokenValue: String

    public suspend fun accessToken(): String

    public val refreshTokenValue: String

    public suspend fun refreshToken(): String
}