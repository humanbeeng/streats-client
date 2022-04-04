package app.streats.client.feature_home.presentation.shop_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.streats.client.core.util.Resource
import app.streats.client.feature_cart.domain.usecase.AddToCart
import app.streats.client.feature_cart.domain.usecase.RemoveFromCart
import app.streats.client.feature_cart.presentation.cart_screen.CartState
import app.streats.client.feature_home.data.repository.ShopRepository
import app.streats.client.feature_home.util.HomeConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class ShopScreenViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val addToCartUC: AddToCart,
    private val removeFromCartUC: RemoveFromCart,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _shopState = mutableStateOf(ShopScreenState())
    val shopState: State<ShopScreenState> = _shopState

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState

    init {
        savedStateHandle.get<String>(HomeConstants.STREATS_SHOP_ID)
            ?.let { shopIdValueFromState -> getShop(shopIdValueFromState) }
    }

    fun shopEventHandler(shopEvent: ShopEvent) {
        when (shopEvent) {
            is ShopEvent.AddToCart -> {
                addToCart(shopEvent.dishId, shopEvent.shopId)
            }

            is ShopEvent.RemoveFromCart -> {
                removeFromCart(shopEvent.dishId, shopEvent.shopId)
            }
        }
    }

    private fun getShop(shopId: String) {
        shopRepository.getShopDetails(shopId).onEach { state ->
            when (state) {
                is Resource.Loading -> {
                    _shopState.value = ShopScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _shopState.value = ShopScreenState(data = state.data)
                }
                is Resource.Error -> {
                    Timber.e(state.message.toString())
                    _shopState.value =
                        ShopScreenState(isLoading = false, error = HomeConstants.HOME_ERROR_MESSAGE)

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
                    _cartState.value = CartState(data = state.data)
                }
                is Resource.Error -> {
                    Timber.e(state.message.toString())
                    _cartState.value =
                        CartState(isLoading = false, error = HomeConstants.HOME_ERROR_MESSAGE)

                }
            }
        }
    }


    private fun removeFromCart(dishId: String, shopId: String) {
        removeFromCartUC(dishId, shopId).onEach { state ->
            when (state) {
                is Resource.Loading -> {
                    _cartState.value = CartState(isLoading = true)
                }
                is Resource.Success -> {
                    _cartState.value = CartState(data = state.data)
                }
                is Resource.Error -> {
                    Timber.e(state.message.toString())
                    _cartState.value =
                        CartState(isLoading = false, error = HomeConstants.HOME_ERROR_MESSAGE)

                }
            }
        }
    }

}