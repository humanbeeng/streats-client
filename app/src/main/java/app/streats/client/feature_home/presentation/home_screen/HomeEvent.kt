package app.streats.client.feature_home.presentation.home_screen

sealed class HomeEvent {
    object Logout : HomeEvent()

    object Refresh : HomeEvent()
}
