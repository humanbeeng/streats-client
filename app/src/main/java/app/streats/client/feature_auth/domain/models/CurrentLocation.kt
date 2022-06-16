package app.streats.client.feature_auth.domain.models

import app.streats.client.core.util.CoreConstants.EMPTY

data class CurrentLocation(
    var latitude: Double = 0.00,
    var longitude: Double = 0.00,
    var subLocality: String = EMPTY
) {
    fun toCurrentLocationCoordinates(): CurrentLocationCoordinates {
        return CurrentLocationCoordinates(latitude, longitude)
    }
}


data class CurrentLocationCoordinates(
    var latitude: Double = 0.00,
    var longitude: Double = 0.00,
)