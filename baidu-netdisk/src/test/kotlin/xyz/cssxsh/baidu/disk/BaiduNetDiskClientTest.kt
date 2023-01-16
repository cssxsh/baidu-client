package xyz.cssxsh.baidu.disk

import xyz.cssxsh.baidu.oauth.*
import java.io.File
import kotlin.random.Random

internal abstract class BaiduNetDiskClientTest {
    protected val client = BaiduNetDiskClient(
        config = object : BaiduNetDiskConfig {
            override val appName: String = System.getenv("BAIDU_NETDISK_APP_NAME")
            override val appId: Long = System.getenv("BAIDU_NETDISK_APP_ID").toLong()
            override val appKey: String = System.getenv("BAIDU_NETDISK_APP_KEY")
            override val secretKey: String = System.getenv("BAIDU_NETDISK_SECRET_KEY")
            override val shareSecret: String = System.getenv("BAIDU_NETDISK_SHARE_SECRET")
            override val shareThirdId: Long = System.getenv("BAIDU_NETDISK_SHARE_THIRD_ID").toLong()
        },
        token = AuthorizeAccessToken(
            accessToken = System.getenv("BAIDU_NETDISK_ACCESS_TOKEN").orEmpty(),
            expiresIn = 100000,
            refreshToken = System.getenv("BAIDU_NETDISK_REFRESH_TOKEN").orEmpty(),
            scope = emptyList(),
            sessionKey = "",
            sessionSecret = ""
        )
    )

    protected fun generateRandomFile(size: Number): File {
        val random = Random(System.currentTimeMillis())
        val file = File.createTempFile("random", "bin")
        file.writeBytes(random.nextBytes(size.toInt()))

        return file
    }
}