package app.streats.client.core.presentation.events

sealed class UIEvent {
    data class Navigate(val route: String) : UIEvent()
}
