package app.streats.client.feature_order.domain.models

import app.streats.client.feature_cart.domain.models.CartItem

data class Order(
    val orderId: String,
    val shopId: String,
    val userId: String,
    val userFcmToken: String,

    val username: String,
    val itemCount: Int,
    val items: Map<String, CartItem>,
    val totalCost: Double,
    val orderedTime: String,
    val arrivalTime: String,
    var orderStatus: String,
    var paymentStatus: String,
    val orderedDate: String
) {
    fun toOrderSummary(): OrderSummary {
        return OrderSummary(
            orderId = orderId,
            orderAmount = totalCost.toString(),
            orderStatus = orderStatus,
            orderItems = toOrderItemsList(items),
            paymentStatus = paymentStatus,
            arrivalTime = arrivalTime,
            orderedDate = orderedDate
        )
    }

    private fun toOrderItemsList(orderItems: Map<String, CartItem>): List<CartItem> {
        return orderItems.map { it.value }.toList()

    }
}
