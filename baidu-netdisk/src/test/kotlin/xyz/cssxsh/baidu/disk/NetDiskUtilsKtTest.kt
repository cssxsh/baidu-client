package xyz.cssxsh.baidu.disk

import org.junit.jupiter.api.*

internal class NetDiskUtilsKtTest {
    @Test
    fun encrypt() {
        val source = "76ff7056abae584a5a1bd8184399d9b3"
        val cipher = source.encryptMD5()
        println(cipher)
        val target = cipher.decryptMD5()
        Assertions.assertEquals(source, target)
    }
}