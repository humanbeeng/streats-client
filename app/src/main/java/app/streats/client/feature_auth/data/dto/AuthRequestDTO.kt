package app.streats.client.feature_auth.data.dto

import app.streats.client.feature_auth.domain.models.CurrentLocationCoordinates

data class AuthRequestDTO(
    val currentLocationCoordinates: CurrentLocationCoordinates,
    val accessToken: String,
    val fcmToken: String
)
