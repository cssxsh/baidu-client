package xyz.cssxsh.baidu.oauth

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
    val refreshToken: String = "",
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

        fun splitScope(text: String) = text.split(SPLIT_REGEX).map { value ->
            requireNotNull(ScopeType.values().find { it.value == value }) { "$value not in ${ScopeType.values().toList()}" }
        }

        override fun deserialize(decoder: Decoder): List<ScopeType> =
            splitScope(decoder.decodeString())

        override fun serialize(encoder: Encoder, value: List<ScopeType>) =
            encoder.encodeString(value.joinToString(" "))
    }
}
