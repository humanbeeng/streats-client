package app.streats.client.core.util

sealed class CoreScreens(val route: String) {
    object ErrorScreen : CoreScreens("error_screen")
}
