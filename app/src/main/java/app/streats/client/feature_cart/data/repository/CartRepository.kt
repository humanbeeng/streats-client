package app.streats.client.feature_cart.data.repository

import android.content.SharedPreferences
import app.streats.client.core.util.Resource
import app.streats.client.feature_auth.util.AuthConstants
import app.streats.client.feature_cart.data.CartApi
import app.streats.client.feature_cart.domain.models.Cart
import app.streats.client.feature_cart.util.CartConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val api: CartApi,
    private val sharedPreferences: SharedPreferences
) {

    fun cart(): Flow<Resource<Cart>> {
        return flow {
            try {
                emit(Resource.Loading())
                val accessToken =
                    sharedPreferences.getString(AuthConstants.ACCESS_TOKEN_PREF, "") ?: ""
                val cartDTO = api.cart(accessToken)
                val cartItems = cartDTO.toCart()
                val cart = Cart(
                    shopId = cartDTO.shopId,
                    cartItems = cartItems,
                    itemCount = cartDTO.itemCount,
                    totalCost = cartDTO.totalCost
                )
                emit(Resource.Success(cart))
            } catch (e: HttpException) {
                emit(Resource.Error(message = e.message()))

            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage ?: CartConstants.CART_ERROR_MESSAGE
                    )
                )
            }
        }
    }
}