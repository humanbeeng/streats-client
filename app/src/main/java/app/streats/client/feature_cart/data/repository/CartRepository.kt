package app.streats.client.feature_cart.data.repository

import app.streats.client.core.domain.models.AccessToken
import app.streats.client.core.util.Resource
import app.streats.client.feature_cart.data.CartApi
import app.streats.client.feature_cart.data.dto.CartRequest
import app.streats.client.feature_cart.domain.models.Cart
import app.streats.client.feature_cart.util.CartConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

/**
 * TODO : Refactor cartDTO mapping
 */

class CartRepository @Inject constructor(
    private val api: CartApi,
    private val accessToken: AccessToken
) {

    fun getCart(): Flow<Resource<Cart>> {
        return flow {
            try {
                emit(Resource.Loading())
                val accessToken =
                    accessToken.value
                val cartDTO = api.cart(accessToken)
                val cart = Cart(
                    shopId = cartDTO.shopId,
                    cartItems = cartDTO.toCartItems(),
                    itemCount = cartDTO.itemCount,
                    totalCost = cartDTO.totalCost
                )
                emit(Resource.Success(cart))
            } catch (e: HttpException) {
                Timber.e(e.message())
                emit(Resource.Error(message = CartConstants.CART_ERROR_MESSAGE))

            } catch (e: IOException) {
                Timber.e(e.localizedMessage.toString())
                emit(
                    Resource.Error(
                        message = CartConstants.CART_ERROR_MESSAGE
                    )
                )
            }
        }
    }


    fun addToCart(dishId: String, shopId: String): Flow<Resource<Cart>> {
        return flow {
            try {
                emit(Resource.Loading())
                val accessToken = accessToken.value
                val request = CartRequest(dishId, shopId)
                val cartDTO = api.addToCart(accessToken, request)

                val cartItems = cartDTO.toCartItems()
                val cart = Cart(
                    shopId = cartDTO.shopId,
                    cartItems = cartItems,
                    itemCount = cartDTO.itemCount,
                    totalCost = cartDTO.totalCost
                )
                emit(Resource.Success(cart))
            } catch (e: HttpException) {
                Timber.e(e.message())
                emit(Resource.Error(message = CartConstants.CART_ERROR_MESSAGE))

            } catch (e: IOException) {
                Timber.e(e.localizedMessage.toString())
                emit(
                    Resource.Error(
                        message = CartConstants.CART_ERROR_MESSAGE
                    )
                )
            }
        }
    }


    fun removeFromCart(dishId: String, shopId: String): Flow<Resource<Cart>> {
        return flow {
            try {
                emit(Resource.Loading())
                val accessToken = accessToken.value
                val request = CartRequest(dishId, shopId)
                val cartDTO = api.removeFromCart(accessToken, request)

                val cartItems = cartDTO.toCartItems()
                val cart = Cart(
                    shopId = cartDTO.shopId,
                    cartItems = cartItems,
                    itemCount = cartDTO.itemCount,
                    totalCost = cartDTO.totalCost
                )
                emit(Resource.Success(cart))
            } catch (e: HttpException) {
                Timber.e(e.message())
                emit(Resource.Error(message = CartConstants.CART_ERROR_MESSAGE))

            } catch (e: IOException) {
                Timber.e(e.localizedMessage.toString())
                emit(
                    Resource.Error(
                        message = CartConstants.CART_ERROR_MESSAGE
                    )
                )
            }
        }
    }
}