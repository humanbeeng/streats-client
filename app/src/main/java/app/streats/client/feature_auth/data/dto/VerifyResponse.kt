package app.streats.client.feature_auth.data.dto

data class VerifyResponse(
    val accessToken: String,
    val isVerified: Boolean
)