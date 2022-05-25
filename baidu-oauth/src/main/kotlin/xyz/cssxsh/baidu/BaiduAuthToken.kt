package xyz.cssxsh.baidu

import kotlinx.coroutines.sync.*
import java.time.*

public interface BaiduAuthToken {

    public val expires: OffsetDateTime

    public val mutex: Mutex

    public val accessTokenValue: String

    public suspend fun accessToken(): String

    public val refreshTokenValue: String

    public suspend fun refreshToken(): String
}