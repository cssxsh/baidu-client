/*
 * Copyright 2019-2022 Mamoe Technologies and contributors.&#10;&#10;此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.&#10;Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.&#10;&#10;https://github.com/cssxsh/baidu-client/blob/dev/LICENSE
 */

package xyz.cssxsh.baidu.aip

import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import xyz.cssxsh.baidu.oauth.BaiduAuthConfig

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AipContentCensorTest : BaiduApiClientTest() {
    override val client: AipClient = BaiduAipClient(config = object : BaiduAuthConfig {
        override val appName: String = System.getenv("BAIDU_CENSOR_APP_NAME")
        override val appId: Long = System.getenv("BAIDU_CENSOR_APP_ID").toLong()
        override val appKey: String = System.getenv("BAIDU_CENSOR_APP_KEY")
        override val secretKey: String = System.getenv("BAIDU_CENSOR_SECRET_KEY")
    })

    private val censor = AipContentCensor(client = client)

    @Test
    fun text(): Unit = runBlocking {
        val result = censor.text(plain = "操你妈")
        println(result)
    }

    @Test
    fun image(): Unit = runBlocking {
        val result = censor.image(url = "https://i0.hdslb.com/bfs/article/572e4d17d1ab4527ded2a943a40c89a93a137b63.jpg")
        println(result)
    }
}