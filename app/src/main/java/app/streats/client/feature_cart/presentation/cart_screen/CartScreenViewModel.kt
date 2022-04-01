package app.streats.client.feature_cart.presentation.cart_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.streats.client.core.util.Resource
import app.streats.client.feature_cart.data.repository.CartRepository
import app.streats.client.feature_cart.util.CartConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState


    init {
        getCart()
    }

    private fun getCart() {
        cartRepository.cart().onEach { state ->
            when (state) {
                is Resource.Loading -> {
                    _cartState.value = CartState(isLoading = true)
                }
                is Resource.Success -> {
                    _cartState.value = CartState(cartData = state.data, isLoading = false)
                }

                is Resource.Error -> {
                    _cartState.value =
                        CartState(isLoading = false, error = CartConstants.CART_ERROR_MESSAGE)
                }
            }
        }.launchIn(viewModelScope)
    }

}