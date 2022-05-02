package app.streats.client.feature_auth.presentation.splash_screen

//TODO : Add other states
data class AuthState(
    val isAuthenticated: Boolean = false,
    val isLoading : Boolean = false
)
