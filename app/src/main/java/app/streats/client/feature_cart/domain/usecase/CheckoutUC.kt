package app.streats.client.feature_cart.domain.usecase

import app.streats.client.core.util.Resource
import app.streats.client.feature_order.data.dto.CheckoutDTO
import app.streats.client.feature_order.data.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CheckoutUC @Inject constructor(
    private val orderRepository: OrderRepository
) {
    operator fun invoke(): Flow<Resource<CheckoutDTO>> {
        return orderRepository.initiateOrder()
    }
}