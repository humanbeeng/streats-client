package app.streats.client.feature_cart.presentation.cart_screen

import app.streats.client.feature_cart.domain.models.Cart

data class CartState(
    val cartData: Cart? = null,
    val error: String = "",
    val isLoading: Boolean = false
)
