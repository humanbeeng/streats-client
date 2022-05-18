package app.streats.client.feature_order.data.repository

import app.streats.client.core.domain.models.AccessToken
import app.streats.client.core.util.CoreConstants.ERROR_MESSAGE
import app.streats.client.core.util.Resource
import app.streats.client.feature_order.data.OrderApi
import app.streats.client.feature_order.data.dto.CheckoutDTO
import app.streats.client.feature_order.domain.models.OrderSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val api: OrderApi,
    private val accessToken: AccessToken
) {

    fun initiateOrder(): Flow<Resource<CheckoutDTO>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = api.initiateOrder(accessToken = accessToken.value)
                val order = response.body()
                if (response.isSuccessful && (order != null)) {
                    emit(Resource.Success(data = order))
                } else {
                    emit(Resource.Error(message = ERROR_MESSAGE))
                }
            } catch (e: Exception) {
                Timber.e(e.localizedMessage);
                emit(Resource.Error(message = ERROR_MESSAGE))

            }
        }
    }


    fun fetchUserOrders(): Flow<Resource<List<OrderSummary>>> {
        return flow {
            try {
                emit(Resource.Loading())

                val response = api.fetchUserOrders(accessToken.value)

                if (response.isSuccessful && response.body() != null) {
                    val orders = response.body()!!
                    val orderSummary = orders.map { it.toOrderSummary() }.toList()
                    emit(Resource.Success(orderSummary))
                } else emit(Resource.Error(message = ERROR_MESSAGE))

            } catch (e: Exception) {
                Timber.e(e.localizedMessage);
                emit(Resource.Error(message = ERROR_MESSAGE))

            }
        }
    }

}