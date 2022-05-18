package app.streats.client.feature_auth.data.dto

import app.streats.client.feature_auth.domain.models.CurrentLocationCoordinates

//TODO : Remove accessToken from AuthRequestDTO since we are already passing in through Header

data class AuthRequestDTO(
    val currentLocationCoordinates: CurrentLocationCoordinates,
    val accessToken: String,
    val fcmToken: String
)
