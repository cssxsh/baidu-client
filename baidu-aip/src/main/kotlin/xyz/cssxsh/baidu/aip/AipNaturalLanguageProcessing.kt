package xyz.cssxsh.baidu.aip

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.baidu.*
import xyz.cssxsh.baidu.aip.nlp.*

/**
 * [ai-doc](https://ai.baidu.com/ai-doc/NLP/fk6z52f2u)
 */
public class AipNaturalLanguageProcessing(override val client: AipClient) : AipApplication {
    public companion object {
        internal const val LEXER = "https://aip.baidubce.com/rpc/2.0/nlp/v1/lexer"
        internal const val WORD_EMB_VEC = "https://aip.baidubce.com/rpc/2.0/nlp/v2/word_emb_vec"
        internal const val WORD_EMB_SIM = "https://aip.baidubce.com/rpc/2.0/nlp/v2/word_emb_sim"
        internal const val DNN_LM_CN = "https://aip.baidubce.com/rpc/2.0/nlp/v2/dnnlm_cn"
        internal const val DEP_PARSER = "https://aip.baidubce.com/rpc/2.0/nlp/v2/depparser"
        internal const val SIM_NET = "https://aip.baidubce.com/rpc/2.0/nlp/v2/simnet"
    }

    public suspend fun lexer(text: String): LexerResult {
        return client.useHttpClient { http ->
            http.post(LEXER) {
                parameter("charset", "UTF-8")
                parameter("access_token", accessToken())

                body = mapOf("text" to text)
                contentType(ContentType.Application.Json)
            }
        }
    }

    public suspend fun similarity(first: String, second: String): SimilarityResult {
        return client.useHttpClient { http ->
            http.post(WORD_EMB_SIM) {
                parameter("charset", "UTF-8")
                parameter("access_token", accessToken())

                body = SimilarityResult.Words(first, second)
                contentType(ContentType.Application.Json)
            }
        }
    }

    public suspend fun dnn(text: String): DNNResult {
        return client.useHttpClient { http ->
            http.post(DNN_LM_CN) {
                parameter("charset", "UTF-8")
                parameter("access_token", accessToken())

                body = mapOf("text" to text)
                contentType(ContentType.Application.Json)
            }
        }
    }

    public suspend fun dependency(text: String): DependencyResult {
        return client.useHttpClient { http ->
            http.post(DEP_PARSER) {
                parameter("charset", "UTF-8")
                parameter("access_token", accessToken())

                body = mapOf("text" to text)
                contentType(ContentType.Application.Json)
            }
        }
    }

    public suspend fun simnet(first: String, second: String): SimnetResult {
        return client.useHttpClient { http ->
            http.post(SIM_NET) {
                parameter("charset", "UTF-8")
                parameter("access_token", accessToken())

                body = SimnetResult.Texts(first, second)
                contentType(ContentType.Application.Json)
            }
        }
    }
}