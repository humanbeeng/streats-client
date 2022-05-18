package app.streats.client.feature_auth.presentation.splash_screen

import app.streats.client.core.util.CoreConstants.ERROR_MESSAGE
import app.streats.client.feature_auth.domain.models.CurrentLocationCoordinates

data class CurrentLocationState(
    val currentLocationCoordinates: CurrentLocationCoordinates = CurrentLocationCoordinates(0.00, 0.00),
    val error: String? = ERROR_MESSAGE,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false
)
