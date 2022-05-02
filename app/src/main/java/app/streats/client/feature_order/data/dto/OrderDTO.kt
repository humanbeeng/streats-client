package app.streats.client.feature_order.data.dto

data class OrderDTO(
    val orderAmount: String,

    val orderCurrency: String,

    val orderId: String,

    val status: String,

    val cftoken: String,

    val stage: String,

    val appId: String
)
