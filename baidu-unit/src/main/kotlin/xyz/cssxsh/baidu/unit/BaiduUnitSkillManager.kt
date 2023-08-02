package xyz.cssxsh.baidu.unit

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import xyz.cssxsh.baidu.unit.data.*

/**
 * [ai-doc](https://ai.baidu.com/ai-doc/UNIT/8kiy75ys2)
 */
public class BaiduUnitSkillManager internal constructor(public val client: BaiduUnitClient) {
    public companion object {
        internal const val LIST_API: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/skill/list"
        internal const val LIST_ADD: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/skill/add"
        internal const val LIST_UPDATE: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/skill/update"
        internal const val LIST_DELETE: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/skill/delete"
        internal const val LIST_INFO: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/skill/info"
    }

    /**
     * 查询技能列表
     * @param pageNo ⻚码, 从 1 开始
     * @param pageSize 每⻚记录数
     * @param category 技能类别
     * @param type 技能类型
     */
    public suspend fun list(
        pageNo: Int,
        pageSize: Int = 200,
        category: SkillCategory? = null,
        type: SkillType? = null
    ): SkillList {
        val body = SkillListRequest(
            category = category,
            type = type,
            pageNo = pageNo,
            pageSize = pageSize
        )
        return client.useHttpClient { http ->
            val responseBody = http.post(LIST_API) {
                url {
                    host = client.config.host
                }

                parameter("access_token", client.accessToken())
                contentType(type = ContentType.Application.Json)
                setBody(body = body)
            }.body<ResponseBody<SkillList>>()

            if (responseBody.errorCode != 0L) throw BaiduUnitException(request = body, response = responseBody)

            responseBody.result ?: throw BaiduUnitException(request = body, response = responseBody)
        }
    }

    /**
     * 新建技能
     * @param name 技能名称，⻓度范围1~30
     * @param description 技能描述，⻓度范围0~50
     * @param type 技能类型
     */
    public suspend fun add(name: String, description: String, type: SkillType): SkillIndex {
        val body = SkillAddRequest(
            name = name,
            description = description,
            type = type
        )
        return client.useHttpClient { http ->
            val responseBody = http.post(LIST_ADD) {
                url {
                    host = client.config.host
                }

                parameter("access_token", client.accessToken())
                contentType(type = ContentType.Application.Json)
                setBody(body = body)
            }.body<ResponseBody<SkillIndex>>()

            if (responseBody.errorCode != 0L) throw BaiduUnitException(request = body, response = responseBody)

            responseBody.result ?: throw BaiduUnitException(request = body, response = responseBody)
        }
    }

    /**
     * 修改技能属性
     * @param id 机器⼈ID
     * @param name 技能名称，⻓度范围1~30
     * @param description 技能描述，⻓度范围0~50
     */
    public suspend fun update(id: String, name: String, description: String) {
        val body = SkillUpdateRequest(
            id = id,
            name = name,
            description = description
        )
        client.useHttpClient { http ->
            val responseBody = http.post(LIST_UPDATE) {
                url {
                    host = client.config.host
                }

                parameter("access_token", client.accessToken())
                setBody(body = body)
            }.body<ResponseBody<JsonObject>>()

            if (responseBody.errorCode != 0L) throw BaiduUnitException(request = body, response = responseBody)
        }
    }

    /**
     * 删除技能
     * @param id 技能ID
     */
    public suspend fun delete(id: Long) {
        val body = SkillIndex(
            id = id
        )
        client.useHttpClient { http ->
            val responseBody = http.post(LIST_DELETE) {
                url {
                    host = client.config.host
                }

                parameter("access_token", client.accessToken())
                contentType(type = ContentType.Application.Json)
                setBody(body = body)
            }.body<ResponseBody<JsonObject>>()

            if (responseBody.errorCode != 0L) throw BaiduUnitException(request = body, response = responseBody)
        }
    }

    /**
     * 查询技能详情
     * @param id 技能ID
     */
    public suspend fun info(id: Long): SkillInfo {
        val body = SkillIndex(
            id = id
        )
        return client.useHttpClient { http ->
            val responseBody = http.post(LIST_INFO) {
                url {
                    host = client.config.host
                }

                parameter("access_token", client.accessToken())
                contentType(type = ContentType.Application.Json)
                setBody(body = body)
            }.body<ResponseBody<SkillInfo>>()

            if (responseBody.errorCode != 0L) throw BaiduUnitException(request = body, response = responseBody)

            responseBody.result ?: throw BaiduUnitException(request = body, response = responseBody)
        }
    }
}