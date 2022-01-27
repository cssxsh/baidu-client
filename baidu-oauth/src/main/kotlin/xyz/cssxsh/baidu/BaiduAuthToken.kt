package xyz.cssxsh.baidu

import java.time.*

public interface BaiduAuthToken {

    public val expires: OffsetDateTime

    public val accessToken: String

    public val refreshToken: String
}