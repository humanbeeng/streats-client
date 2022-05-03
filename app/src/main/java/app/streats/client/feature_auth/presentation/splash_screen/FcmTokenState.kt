package app.streats.client.feature_auth.presentation.splash_screen

import app.streats.client.core.util.Constants.EMPTY

data class FcmTokenState(
    val fcmToken: String = EMPTY,
    val error: String? = EMPTY,
    val isSuccessful: Boolean = false
)
