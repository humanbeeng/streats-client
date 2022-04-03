package app.streats.client.feature_cart.data.dto

import app.streats.client.feature_cart.domain.models.CartItem

data class CartDTO(
    val shopId: String,
    val itemCount: Int,
    val cartItems: MutableMap<String, CartItemDTO>,
    val totalCost: Double
) {
    fun toCartItems(): List<CartItem> {
        val cart: MutableList<CartItem> = mutableListOf()
        cartItems.map { cartItem ->
            cart.add(
                CartItem(
                    dishId = cartItem.key,
                    itemName = cartItem.value.itemName,
                    price = cartItem.value.price,
                    quantity = cartItem.value.quantity,
                    shopId = cartItem.value.shopId
                )
            )
        }
        return cart
    }

}
