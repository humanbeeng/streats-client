package app.streats.client.feature_auth.presentation.login_screen

data class LoginState(
    val isLoggedIn: Boolean = false,
    val error: String = "",
    val isLoading: Boolean = false
)