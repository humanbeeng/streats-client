package app.streats.client.feature_home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.streats.client.core.util.Resource
import app.streats.client.feature_auth.data.repository.AuthRepository
import app.streats.client.feature_home.data.repository.HomeRepository
import app.streats.client.feature_home.util.HomeConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _homeState = mutableStateOf(HomeScreenState())
    val homeState: State<HomeScreenState> = _homeState

    init {
        getHome()
    }

    fun logout() {
        authRepository.logout()
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

}