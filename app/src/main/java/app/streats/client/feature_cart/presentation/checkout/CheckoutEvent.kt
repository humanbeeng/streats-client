package app.streats.client.feature_cart.presentation.checkout

sealed class CheckoutEvent {
    object StartPayment : CheckoutEvent()
}