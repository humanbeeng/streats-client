package app.streats.client.feature_auth.util

sealed class AuthScreens(val route: String) {
    object LoginScreen : AuthScreens("login_screen")
    object SplashScreen : AuthScreens("splash_screen")
}
