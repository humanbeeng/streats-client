package app.streats.client.feature_cart.presentation.cart_screen

import app.streats.client.core.util.CoreConstants.EMPTY
import app.streats.client.feature_cart.domain.models.Cart

data class CartState(
    val data: Cart? = null,
    val error: String = EMPTY,
    val isLoading: Boolean = false
)
