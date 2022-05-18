package app.streats.client.feature_order.domain.models

import app.streats.client.feature_cart.domain.models.CartItem

data class OrderSummary(
    val orderId: String,
    val orderAmount: String,
    val orderStatus: String,
    val orderItems: List<CartItem>,
    val paymentStatus: String,
    val arrivalTime: String,
    val orderedDate: String

)