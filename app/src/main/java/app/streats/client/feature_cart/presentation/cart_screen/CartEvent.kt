package app.streats.client.feature_cart.presentation.cart_screen

sealed class CartEvent {
    class AddToCart(val dishId: String, val shopId: String) : CartEvent()

    class RemoveFromCart(val dishId: String, val shopId: String) : CartEvent()
    object Checkout : CartEvent()


}