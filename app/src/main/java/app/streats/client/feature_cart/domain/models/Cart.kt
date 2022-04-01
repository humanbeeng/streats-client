package app.streats.client.feature_cart.domain.models

data class Cart(
    val shopId: String,
    val itemCount: Int,
    val cartItems: List<CartItem>,
    val totalCost: Double
)
