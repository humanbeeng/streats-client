package app.streats.client.feature_home.presentation.shop_screen

sealed class ShopEvent{
    class AddToCart(val dishId : String, val shopId: String) : ShopEvent()
    class RemoveFromCart(val dishId: String, val shopId: String) : ShopEvent()
}
