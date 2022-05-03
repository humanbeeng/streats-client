package app.streats.client.feature_auth.presentation.login_screen

import app.streats.client.core.util.Constants.EMPTY

data class LoginState(
    val isLoggedIn: Boolean = false,
    val error: String = EMPTY,
    val isLoading: Boolean = false
)