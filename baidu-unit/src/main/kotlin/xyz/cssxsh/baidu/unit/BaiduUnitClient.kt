package xyz.cssxsh.baidu.unit

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.baidu.aip.*
import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.unit.data.*
import java.util.UUID

/**
 * [ai-doc](https://ai.baidu.com/ai-doc/UNIT/qkpzeloou)
 */
public open class BaiduUnitClient(override val config: BaiduUnitConfig) : AipClient, AbstractAipClient() {
    public companion object {
        internal const val CHAT_API: String = "https://aip.baidubce.com/rpc/2.0/unit/service/v3/chat"
    }

    public constructor(config: BaiduUnitConfig, token: AuthorizeAccessToken) : this(config) {
        save(token = token)
    }

    protected open val sessions: MutableMap<String, String> = HashMap()

    protected open fun generateLogId(): String = UUID.randomUUID().toString()

    /**
     * 发送一个对话, 用于复杂的情况
     */
    public suspend fun request(body: QueryRequestBody): QueryData {
        require(body.serviceId.isNotEmpty() || body.skillIds.isNotEmpty()) { "service_id 与 skill_ids 不能同时缺失，至少一个有值" }
        val responseBody = useHttpClient { http ->
            http.post(CHAT_API) {
                url {
                    host = config.host
                }

                parameter("access_token", accessToken())

                contentType(type = ContentType.Application.Json)
                setBody(body = body)
            }.body<ResponseBody<QueryData>>()
        }
        if (responseBody.errorCode != 0L) throw BaiduUnitException(request = body, response = responseBody)
        val data = responseBody.result ?: throw BaiduUnitException(request = body, response = responseBody)
        sessions[body.request.terminalId] = data.sessionId

        return data
    }

    /**
     * 发送一个简单的对话
     */
    public suspend fun query(text: String, serviceId: String, terminalId: String): QueryData {
        val body = QueryRequestBody(
            serviceId = serviceId,
            logId = generateLogId(),
            sessionId = sessions[terminalId].orEmpty(),
            request = QueryRequestBody.Request(
                terminalId = terminalId,
                query = text
            )
        )
        return request(body = body)
    }

    /**
     * 机器⼈管理
     */
    public val service: BaiduUnitServiceManager by lazy { BaiduUnitServiceManager(client = this) }

    /**
     * 技能管理
     */
    public val skill: BaiduUnitSkillManager by lazy { BaiduUnitSkillManager(client = this) }
}