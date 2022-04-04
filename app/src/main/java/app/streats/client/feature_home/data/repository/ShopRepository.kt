package app.streats.client.feature_home.data.repository

import app.streats.client.core.util.AccessToken
import app.streats.client.core.util.Resource
import app.streats.client.feature_home.data.ShopApi
import app.streats.client.feature_home.data.dto.ShopDTO
import app.streats.client.feature_home.util.HomeConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShopRepository @Inject constructor(
    private val api: ShopApi,
    private val accessToken: AccessToken
) {

    fun getShopDetails(shopId: String): Flow<Resource<ShopDTO>> {

        return flow {
            try {
                emit(Resource.Loading())
                val accessToken = accessToken.value
                val shop = api.getShopByShopId(accessToken, shopId)
                emit(Resource.Success(shop))
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.message()))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: HomeConstants.HOME_ERROR_MESSAGE
                    )
                )
            }
        }

    }

}