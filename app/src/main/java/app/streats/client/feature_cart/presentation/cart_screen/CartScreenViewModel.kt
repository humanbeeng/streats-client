package app.streats.client.feature_cart.presentation.cart_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.streats.client.core.util.Resource
import app.streats.client.feature_cart.data.repository.CartRepository
import app.streats.client.feature_cart.domain.usecase.AddToCart
import app.streats.client.feature_cart.domain.usecase.CheckoutUC
import app.streats.client.feature_cart.domain.usecase.RemoveFromCart
import app.streats.client.feature_cart.presentation.checkout.CheckoutState
import app.streats.client.feature_cart.util.CartConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * TODO : Add error dialogue box to show dish item from different shop
 * TODO: Add Checkout flow
 */

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val addToCartUC: AddToCart,
    private val removeFromCartUC: RemoveFromCart,
    private val checkoutUC: CheckoutUC,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState

    /**
     * Channel:
     *
     * Channels are used to send one time events the UI layer/observables. Since LaunchedEffect was
     * being fired off twice for some reason, Channel is used as it allows only one observable
     *
     * Apart from that, instead of initializing the observable(collectAsState etc) inside the
     * composable, here, its being initialized in VM itself as it guarantees only one observable
     * is created. This initialized observable can be accessed directly through viewModel object
     */
    private val _checkoutState = Channel<CheckoutState>()
    val checkoutState = _checkoutState.receiveAsFlow()

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
                is CartEvent.Checkout -> {
                    checkout()
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

    private fun checkout() {
        checkoutUC().onEach { state ->
            when (state) {
                is Resource.Loading -> {
                    _checkoutState.send(CheckoutState.Loading)
                }
                is Resource.Success -> {
                    Timber.d("Order Initiate success")
                    _checkoutState.send(CheckoutState.Success(state.data!!))
                }

                is Resource.Error -> {
                    _checkoutState.send(CheckoutState.Error)

                }
            }
        }.launchIn(viewModelScope)
    }
}