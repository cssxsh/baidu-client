package xyz.cssxsh.baidu.unit

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import xyz.cssxsh.baidu.unit.data.*

/**
 * [ai-doc](https://ai.baidu.com/ai-doc/UNIT/8kiy75ys2)
 */
public class BaiduUnitServiceManager internal constructor(public val client: BaiduUnitClient) {
    public companion object {
        internal const val LIST_API: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/service/list"
        internal const val LIST_ADD: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/service/add"
        internal const val LIST_UPDATE: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/service/update"
        internal const val LIST_DELETE: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/service/delete"
        internal const val LIST_INFO: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/service/info"
        internal const val LIST_SKILL: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/service/listSkill"
        internal const val ADD_SKILL: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/service/addSkill"
        internal const val SORT_SKILL: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/service/sortSkill"
        internal const val DELETE_SKILL: String = "https://aip.baidubce.com/rpc/2.0/unit/v3/service/deleteSkill"
    }

    /**
     * 查询机器⼈列表
     * @param pageNo ⻚码, 从1开始
     * @param pageSize 每⻚记录数
     */
    public suspend fun list(pageNo: Int, pageSize: Int = 200): ServiceList {
        val body = ServiceListRequest(
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
            }.body<ResponseBody<ServiceList>>()

            if (responseBody.errorCode != 0L) throw BaiduUnitException(request = body, response = responseBody)

            responseBody.result ?: throw BaiduUnitException(request = body, response = responseBody)
        }
    }

    /**
     * 新建机器⼈
     * @param name 机器⼈名称
     * @param description 机器⼈描述
     * @param category 机器⼈类型：common(通⽤机器⼈)、marketingClue(营销线索机器⼈)；默认为common
     * @param type 机器⼈类别：
     * * common(通⽤机器⼈)包括：dialogueDistribution（对话分发）、taskflow;
     * * marketingClue包括：general(通⽤场景)、edu（教育场景）、date（相亲场景）；默认为dialogueDistribution
     */
    public suspend fun add(name: String, description: String, category: ServiceCategory, type: ServiceType): ServiceIndex {
        val body = ServiceAddRequest(
            name = name,
            description = description,
            category = category,
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
            }.body<ResponseBody<ServiceIndex>>()

            if (responseBody.errorCode != 0L) throw BaiduUnitException(request = body, response = responseBody)

            responseBody.result ?: throw BaiduUnitException(request = body, response = responseBody)
        }
    }

    /**
     * 修改机器⼈属性
     * @param id 机器⼈ID
     * @param name 机器⼈名称
     * @param description 机器⼈描述
     */
    public suspend fun update(id: String, name: String, description: String) {
        val body = ServiceUpdateRequest(
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
     * 删除机器⼈
     * @param id 机器⼈ID
     */
    public suspend fun delete(id: String) {
        val body = ServiceIndex(
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
     * 查询机器⼈详情
     * @param id 机器⼈ID
     */
    public suspend fun info(id: String): ServiceInfo {
        val body = ServiceIndex(
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
            }.body<ResponseBody<ServiceInfo>>()

            if (responseBody.errorCode != 0L) throw BaiduUnitException(request = body, response = responseBody)

            responseBody.result ?: throw BaiduUnitException(request = body, response = responseBody)
        }
    }

    /**
     * 查询机器⼈技能列表
     * @param id 机器⼈ID
     * @param pageNo ⻚码, 从1开始
     * @param pageSize 每⻚记录数
     */
    public suspend fun listSkill(id: String, pageNo: Int, pageSize: Int = 200): ServiceSkillList {
        val body = ServiceListSkillRequest(
            id = id,
            pageNo = pageNo,
            pageSize = pageSize
        )
        return client.useHttpClient { http ->
            val responseBody = http.post(LIST_SKILL) {
                url {
                    host = client.config.host
                }

                parameter("access_token", client.accessToken())
                contentType(type = ContentType.Application.Json)
                setBody(body = body)
            }.body<ResponseBody<ServiceSkillList>>()

            if (responseBody.errorCode != 0L) throw BaiduUnitException(request = body, response = responseBody)

            responseBody.result ?: throw BaiduUnitException(request = body, response = responseBody)
        }
    }

    /**
     * 添加技能
     * @param id 机器⼈ID
     * @param skillIds 技能ID列表
     */
    public suspend fun addSkill(id: String, skillIds: List<Long>) {
        val body = ServiceSkillsRequest(
            serviceId = id,
            skillIds = skillIds
        )
        return client.useHttpClient { http ->
            val responseBody = http.post(ADD_SKILL) {
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
     * 修改技能优先级
     * @param id 机器⼈ID
     * @param skillIds 技能ID列表
     */
    public suspend fun sortSkill(id: String, skillIds: List<Long>) {
        val body = ServiceSkillsRequest(
            serviceId = id,
            skillIds = skillIds
        )
        return client.useHttpClient { http ->
            val responseBody = http.post(SORT_SKILL) {
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
     * 移除技能
     * @param id 机器⼈ID
     * @param skillId 技能ID
     */
    public suspend fun deleteSkill(id: String, skillId: Long) {
        val body = ServiceSkillRequest(
            serviceId = id,
            skillId = skillId
        )
        return client.useHttpClient { http ->
            val responseBody = http.post(DELETE_SKILL) {
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
}