package app.streats.client.feature_auth.presentation.splash_screen

data class FcmTokenState(
    val fcmToken: String = "",
    val error: String? = "",
    val isSuccessful: Boolean = false
)
