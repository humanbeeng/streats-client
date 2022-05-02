package app.streats.client.feature_auth.data.dto

data class LoginResponseDTO(
    val accessToken: String,
    val isVerified: Boolean
)