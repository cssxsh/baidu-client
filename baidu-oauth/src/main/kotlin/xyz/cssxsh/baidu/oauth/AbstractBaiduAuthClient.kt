package xyz.cssxsh.baidu.oauth

import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.coroutines.sync.*
import xyz.cssxsh.baidu.api.*
import xyz.cssxsh.baidu.oauth.data.*
import xyz.cssxsh.baidu.oauth.exception.*
import java.time.*

public abstract class AbstractBaiduAuthClient<C : BaiduAuthConfig> : AbstractBaiduApiClient<C>(), BaiduAuthClient<C> {

    protected override val timeout: Long get() = BaiduUserAuthClient.TIMEOUT

    override suspend fun callRequestExceptionHandle(cause: Throwable, request: HttpRequest) {
        if (cause is ClientRequestException) {
            throw try {
                AuthorizeException(cause)
            } catch (cause: Throwable) {
                return
            }
        }
    }

    protected open val status: BaiduAuthStatus = BaiduAuthTokenData()

    override val mutex: Mutex = Mutex()

    override val scope: List<String> get() = status.scope

    override suspend fun accessToken(): String {
        return status.accessTokenValue.takeIf { status.expires >= OffsetDateTime.now() && it.isNotBlank() }
            ?: refresh().accessToken
    }

    override suspend fun refreshToken(): String {
        return status.refreshTokenValue.takeIf { it.isNotBlank() }
            ?: throw NotTokenException("RefreshToken", this)
    }

    protected fun save(token: AuthorizeAccessToken) {
        status.save(token)
    }

    public override suspend fun save(block: suspend () -> AuthorizeAccessToken): AuthorizeAccessToken {
        return mutex.withLock {
            block().also { save(it) }
        }
    }
}