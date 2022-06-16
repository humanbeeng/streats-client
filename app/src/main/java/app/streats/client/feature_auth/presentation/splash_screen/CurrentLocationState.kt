package app.streats.client.feature_auth.presentation.splash_screen

import app.streats.client.core.util.CoreConstants.EMPTY
import app.streats.client.core.util.CoreConstants.ERROR_MESSAGE
import app.streats.client.feature_auth.domain.models.CurrentLocation

data class CurrentLocationState(
    val currentLocation: CurrentLocation = CurrentLocation(0.00, 0.00),
    val subLocality: String = EMPTY,
    val error: String? = ERROR_MESSAGE,
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false
)
