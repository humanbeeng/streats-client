package app.streats.client.feature_auth.data.dto

import app.streats.client.feature_auth.domain.models.CurrentLocationCoordinates

//TODO : Add Sublocality in user
data class LoginRequestDTO(
    val currentLocationCoordinates: CurrentLocationCoordinates,
    val fcmToken: String,
    val idToken: String
)