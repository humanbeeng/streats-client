package app.streats.client.feature_cart.domain.models

data class CartItem(
    val dishId: String,
    val shopId: String,
    val itemName: String,
    val quantity: Int,
    val price: Double
)
