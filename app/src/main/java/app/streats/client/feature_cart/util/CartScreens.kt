package app.streats.client.feature_cart.util

sealed class CartScreens(val route: String) {
    object CartScreen : CartScreens("cart_screen")
}
