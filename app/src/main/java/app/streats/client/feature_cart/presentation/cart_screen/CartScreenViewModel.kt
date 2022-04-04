package app.streats.client.feature_cart.presentation.cart_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.streats.client.core.util.Resource
import app.streats.client.feature_cart.data.repository.CartRepository
import app.streats.client.feature_cart.domain.usecase.AddToCart
import app.streats.client.feature_cart.domain.usecase.RemoveFromCart
import app.streats.client.feature_cart.util.CartConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val addToCartUC: AddToCart,
    private val removeFromCartUC : RemoveFromCart,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState


    init {
        getCart()
    }

    fun cartEventHandler(cartEvent: CartEvent) {
        viewModelScope.launch {
            when (cartEvent) {
                is CartEvent.AddToCart -> {
                    addToCart(cartEvent.dishId, cartEvent.shopId)
                }
                is CartEvent.RemoveFromCart -> {
                    removeFromCart(cartEvent.dishId, cartEvent.shopId)
                }
            }
        }
    }

    private fun getCart() {
        cartRepository.getCart().onEach { state ->
            when (state) {
                is Resource.Loading -> {
                    _cartState.value = CartState(isLoading = true)
                }
                is Resource.Success -> {
                    _cartState.value = CartState(data = state.data, isLoading = false)
                }

                is Resource.Error -> {
                    _cartState.value =
                        CartState(isLoading = false, error = CartConstants.CART_ERROR_MESSAGE)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addToCart(dishId: String, shopId: String) {
        addToCartUC(dishId, shopId).onEach { state ->
            when (state) {
                is Resource.Loading -> {
                    _cartState.value = CartState(isLoading = true)
                }
                is Resource.Success -> {
                    _cartState.value = CartState(data = state.data, isLoading = false)
                }

                is Resource.Error -> {
                    _cartState.value =
                        CartState(isLoading = false, error = CartConstants.CART_ERROR_MESSAGE)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun removeFromCart(dishId: String, shopId: String) {
        removeFromCartUC(dishId, shopId).onEach { state ->
            when (state) {
                is Resource.Loading -> {
                    _cartState.value = CartState(isLoading = true)
                }
                is Resource.Success -> {
                    _cartState.value = CartState(data = state.data, isLoading = false)
                }

                is Resource.Error -> {
                    _cartState.value =
                        CartState(isLoading = false, error = CartConstants.CART_ERROR_MESSAGE)
                }
            }
        }.launchIn(viewModelScope)
    }
}