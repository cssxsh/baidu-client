package xyz.cssxsh.baidu.unit

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.baidu.aip.*
import xyz.cssxsh.baidu.oauth.*
import xyz.cssxsh.baidu.unit.data.*
import java.util.UUID

public open class BaiduUnitClient(override val config: BaiduUnitConfig) : AipClient, AbstractAipClient() {
    public constructor(config: BaiduUnitConfig, token: AuthorizeAccessToken) : this(config) {
        save(token = token)
    }
    protected val sessions: MutableMap<String, String> = HashMap()

    public suspend fun request(body: RequestBody): ResponseData {
        require(body.serviceId.isNotEmpty() || body.skillIds.isNotEmpty()) { "service_id 与 skill_ids 不能同时缺失，至少一个有值" }
        val responseBody = useHttpClient { http ->
            http.post(config.chat) {
                parameter("access_token", accessToken())

                contentType(type = ContentType.Application.Json)
                setBody(body = body)
            }.body<ResponseBody>()
        }
        if (responseBody.errorCode != 0L) throw BaiduUnitException(request = body, response = responseBody)
        val data = responseBody.result ?: throw BaiduUnitException(request = body, response = responseBody)
        sessions[body.request.terminalId] = data.sessionId

        return data
    }

    public suspend fun query(text: String, serviceId: String, terminalId: String): ResponseData {
        val body = RequestBody(
            serviceId = serviceId,
            logId = UUID.randomUUID().toString(),
            sessionId = sessions[terminalId].orEmpty(),
            request = RequestBody.Request(
                terminalId = terminalId,
                query = text
            )
        )
        return request(body = body)
    }
}