package app.streats.client.feature_cart.data.dto


data class CartItemDTO(
    val dishId: String,
    val shopId: String,
    val itemName: String,
    val quantity: Int,
    val price: Double
)