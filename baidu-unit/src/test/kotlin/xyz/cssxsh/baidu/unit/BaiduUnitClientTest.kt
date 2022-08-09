package xyz.cssxsh.baidu.unit

import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import java.util.UUID

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class BaiduUnitClientTest {
    private val unit = BaiduUnitClient(config = object : BaiduUnitConfig {
        override val host: String = BaiduUnitConfig.HOST.DEV
        override val appName: String = System.getenv("BAIDU_UNIT_APP_NAME")
        override val appId: Long = System.getenv("BAIDU_UNIT_APP_ID").toLong()
        override val appKey: String = System.getenv("BAIDU_UNIT_APP_KEY")
        override val secretKey: String = System.getenv("BAIDU_UNIT_SECRET_KEY")
    })

    @BeforeAll
    fun init(): Unit = runBlocking {
        unit.token()
    }

    @Test
    fun query(): Unit = runBlocking {
        val data = unit.query(text = "你叫什么名字", serviceId = "S73154", terminalId = UUID.randomUUID().toString())

        for (response in data.responses) {
            println("=== ${response.origin} ===")
            for (action in response.actions) {
                println("${action.type}: ${action.say}")
            }
        }
    }

    @Test
    fun service(): Unit = runBlocking {
        val services = unit.service.list(pageNo = 1).services

        for (service in services) {
            val info = unit.service.info(id = service.id)

            println("=== ${info.name} - ${info.category} - ${info.type} ===")
            val skills = unit.service.listSkill(id = info.id, pageNo = 1).skills

            for (skill in skills) {
                println("${skill.name} - ${info.category} - ${info.type}")
            }
        }
    }

    @Test
    fun skill(): Unit = runBlocking {
        val skills = unit.skill.list(pageNo = 1).skills

        for (skill in skills) {
            val info = unit.skill.info(id = skill.id)

            println("${info.name} - ${info.category} - ${info.type}")
        }
    }
}