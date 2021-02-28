package xyz.cssxsh.baidu.auth

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import xyz.cssxsh.baidu.SecondUnit

@Serializable
data class AuthorizeAccessToken(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: SecondUnit,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("scope")
    @Serializable(with = ScopeSerializer::class)
    val scope: List<ScopeType>,
    @SerialName("session_key")
    val sessionKey: String,
    @SerialName("session_secret")
    val sessionSecret: String
) {
    object ScopeSerializer : KSerializer<List<ScopeType>> {
        override val descriptor: SerialDescriptor
            get() = buildSerialDescriptor("ScopeSerializer", PrimitiveKind.STRING)

        private val SPLIT_REGEX = """([,]|\s)""".toRegex()

        override fun deserialize(decoder: Decoder): List<ScopeType> =
            decoder.decodeString().split(SPLIT_REGEX).map { name -> ScopeType.valueOf(value = name.toUpperCase()) }

        override fun serialize(encoder: Encoder, value: List<ScopeType>) =
            encoder.encodeString(value.joinToString(" "))
    }
}
