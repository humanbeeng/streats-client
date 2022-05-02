package app.streats.client.feature_cart.presentation.checkout

import app.streats.client.feature_order.data.dto.OrderDTO


sealed class CheckoutState {
    object Loading: CheckoutState()
    object Error: CheckoutState()
    data class Success(val data: OrderDTO) : CheckoutState()
}
