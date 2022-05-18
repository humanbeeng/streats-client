package app.streats.client.feature_order.presentation.order_history

import app.streats.client.core.util.CoreConstants.EMPTY
import app.streats.client.feature_order.domain.models.OrderSummary

data class OrderHistoryState(
    val data: List<OrderSummary>? = null,
    val isLoading: Boolean = false,
    val error: String = EMPTY
)
