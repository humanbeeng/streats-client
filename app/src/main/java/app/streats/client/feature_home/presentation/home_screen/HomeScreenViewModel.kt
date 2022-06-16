package app.streats.client.feature_home.presentation.home_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.streats.client.core.util.Resource
import app.streats.client.feature_auth.data.repository.AuthRepository
import app.streats.client.feature_auth.domain.models.CurrentLocation
import app.streats.client.feature_home.data.repository.HomeRepository
import app.streats.client.feature_home.util.HomeConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val homeRepository: HomeRepository,
    val currentLocation: CurrentLocation
) : ViewModel() {

    private val _outgoingHomeScreenEventFlow = MutableSharedFlow<HomeOutgoingEvent>()
    val outgoingHomeScreenEventFlow = _outgoingHomeScreenEventFlow.asSharedFlow()

    private val _homeState = mutableStateOf(HomeScreenState())
    val homeState: State<HomeScreenState> = _homeState

    init {
        getHome()

    }

    fun homeEventListener(event: HomeEvent) {
        when (event) {
            is HomeEvent.Logout -> {
                logout()
            }
            HomeEvent.Refresh -> getHome()
        }
    }

    private fun getHome() {
        homeRepository.home().onEach { state ->
            when (state) {
                is Resource.Loading -> {
                    _homeState.value = HomeScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _homeState.value = HomeScreenState(data = state.data, isLoading = false)
                }

                is Resource.Error -> {
                    _homeState.value = HomeScreenState(
                        isLoading = false,
                        error = HomeConstants.HOME_ERROR_MESSAGE
                    )
                }

            }
        }.launchIn(viewModelScope)
    }


    private fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _outgoingHomeScreenEventFlow.emit(HomeOutgoingEvent.Logout)
        }
    }

}
