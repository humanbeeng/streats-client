package app.streats.client.feature_order.presentation.order_history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.streats.client.core.util.CoreConstants.ERROR_MESSAGE
import app.streats.client.core.util.Resource
import app.streats.client.feature_order.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _orderHistoryState = mutableStateOf(OrderHistoryState())
    val orderHistoryState: State<OrderHistoryState> = _orderHistoryState


    init {
        fetchUserOrders()
    }


    private fun fetchUserOrders() {
        orderRepository.fetchUserOrders().onEach { state ->
            when (state) {
                is Resource.Loading -> {
                    _orderHistoryState.value = OrderHistoryState(isLoading = true)
                }
                is Resource.Success -> {
                    _orderHistoryState.value = OrderHistoryState(data = state.data, isLoading = false)
                }
                is Resource.Error -> {
                    _orderHistoryState.value = OrderHistoryState(isLoading = false, error = ERROR_MESSAGE)
                    Timber.e("Error occurred while fetching user orders")
                }
            }
        }.launchIn(viewModelScope)
    }

}