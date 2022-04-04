package app.streats.client.feature_cart.domain.usecase

import app.streats.client.core.util.Resource
import app.streats.client.feature_cart.data.repository.CartRepository
import app.streats.client.feature_cart.domain.models.Cart
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddToCart @Inject constructor(private val cartRepository: CartRepository) {

    operator fun invoke(dishId: String, shopId: String): Flow<Resource<Cart>> {
        return cartRepository.addToCart(dishId, shopId)
    }

}