package app.streats.client.core.presentation.events

sealed class UIEvent {

    class Navigate(val route: String) : UIEvent()

    object Error : UIEvent()

    object Loading : UIEvent()

}
