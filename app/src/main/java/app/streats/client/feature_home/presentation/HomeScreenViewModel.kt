package app.streats.client.feature_home.presentation

import androidx.lifecycle.ViewModel
import app.streats.client.feature_auth.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun logout() {
        authRepository.logout()
    }

}