package xyz.cssxsh.baidu

import java.time.*

interface BaiduAuthToken {

    val expires: OffsetDateTime

    val accessToken: String

    val refreshToken: String
}