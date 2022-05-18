package app.streats.client.feature_home.data.repository

import app.streats.client.core.domain.models.AccessToken
import app.streats.client.core.util.Resource
import app.streats.client.feature_home.data.HomeApi
import app.streats.client.feature_home.data.dto.HomeDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: HomeApi,
    private val accessToken: AccessToken
) {

    fun home(): Flow<Resource<HomeDTO>> {
        return flow {
            try {
                emit(Resource.Loading())
                val accessToken = accessToken.value
                val home = api.home(accessToken)
                emit(Resource.Success(home))
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.message()))
            } catch (e: IOException) {
                emit(Resource.Error(message = e.localizedMessage))
            }
        }
    }
}
