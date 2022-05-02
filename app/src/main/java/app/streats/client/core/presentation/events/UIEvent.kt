package app.streats.client.core.presentation.events

sealed class UIEvent {
    class Navigate(val route: String) : UIEvent()

    object Ready : UIEvent()

    object Authenticated: UIEvent()

}
