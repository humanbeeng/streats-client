package app.streats.client.feature_auth.presentation.login_screen

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.streats.client.core.presentation.events.UIEvent
import app.streats.client.core.util.Resource
import app.streats.client.feature_auth.data.repository.AuthRepository
import app.streats.client.feature_auth.util.AuthConstants
import app.streats.client.feature_home.util.HomeScreens
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val sharedPreferences: SharedPreferences,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _outgoingEventFlow = MutableSharedFlow<UIEvent>()
    val outgoingEventFlow = _outgoingEventFlow.asSharedFlow()

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState


    fun loginEventHandler(loginEvent: LoginEvent) {

        viewModelScope.launch {
            when (loginEvent) {
                is LoginEvent.Login -> {
                    _loginState.value = LoginState(isLoading = true, error = "")
                    login(loginEvent.task)

                }

                is LoginEvent.Logout -> {
                    _loginState.value = LoginState(isLoggedIn = false)
                    logout(firebaseAuth)
                }
            }
        }

    }


    private fun login(task: Task<GoogleSignInAccount>?) {
        viewModelScope.launch {
            when (val loginResult = authRepository.loginWithGoogle(task)) {
                is Resource.Loading -> {
                    _loginState.value = LoginState(isLoading = true)

                }

                is Resource.Success -> {
                    _loginState.value = LoginState(isLoading = false, isLoggedIn = true)
                    _outgoingEventFlow.emit(UIEvent.Navigate(HomeScreens.HomeScreen.route))
                }

                is Resource.Error -> {
                    _loginState.value =
                        LoginState(error = loginResult.message ?: AuthConstants.ERROR_STRING)
                }
            }

        }
    }

    private fun logout(firebaseAuth: FirebaseAuth) {
        firebaseAuth.signOut()

    }
}