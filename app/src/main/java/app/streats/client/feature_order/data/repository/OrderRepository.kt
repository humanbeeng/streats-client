package app.streats.client.feature_order.data.repository

import app.streats.client.core.domain.models.AccessToken
import app.streats.client.core.util.Constants
import app.streats.client.core.util.Resource
import app.streats.client.feature_order.data.OrderApi
import app.streats.client.feature_order.data.dto.OrderDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val api: OrderApi,
    private val accessToken: AccessToken
) {

    fun initiateOrder(): Flow<Resource<OrderDTO>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = api.initiateOrder(accessToken = accessToken.value)
                val order = response.body()
                if (response.isSuccessful && (order != null)) {
                    emit(Resource.Success(data = order))
                } else {
                    emit(Resource.Error(message = Constants.ERROR_MESSAGE))
                }
            } catch (e: HttpException) {
                Timber.e(e.message());
                emit(Resource.Error(message = Constants.ERROR_MESSAGE))

            } catch (e: IOException) {
                Timber.e(e.localizedMessage);
                emit(Resource.Error(message = Constants.ERROR_MESSAGE))
            }

        }
    }


}