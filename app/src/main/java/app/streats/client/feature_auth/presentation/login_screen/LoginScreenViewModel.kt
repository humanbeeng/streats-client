package app.streats.client.feature_auth.presentation.login_screen

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
    private val authRepository: AuthRepository,
    private val sharedPreferences: SharedPreferences
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
                    logout()
                }
            }
        }

    }


    private fun login(task: Task<GoogleSignInAccount>?) {
        val googleSignInAccount = task?.getResult(ApiException::class.java)
        if (googleSignInAccount != null) {
            val idToken = task.result.idToken!!
            val credentials = GoogleAuthProvider.getCredential(idToken, null)

            FirebaseAuth.getInstance().signInWithCredential(credentials)
                .addOnCompleteListener { signInWithCredentialTask ->
                    if (signInWithCredentialTask.isSuccessful) {

                        FirebaseAuth.getInstance().currentUser?.getIdToken(false)
                            ?.addOnCompleteListener { idTokenTask ->

                                if (idTokenTask.isSuccessful) {
                                    val token = idTokenTask.result.token!!
                                    viewModelScope.launch {
                                        when (authRepository.verifyToken(token)) {
                                            is Resource.Error -> {
                                            }
                                            is Resource.Success -> {
                                                _outgoingEventFlow.emit(UIEvent.Navigate(HomeScreens.HomeScreen.route))
                                            }
                                            is Resource.Loading -> {

                                            }
                                        }
                                    }

                                    Timber.d("Sign in with credentials success. ")
                                } else {
                                    sharedPreferences.edit()
                                        .putString(AuthConstants.GOOGLE_SIGN_IN_TOKEN_PREF, "")
                                        .apply()
                                    Timber.e("Sign in failed while fetching token.")

                                }
                            }
                    } else {
                        sharedPreferences.edit()
                            .putString(AuthConstants.GOOGLE_SIGN_IN_TOKEN_PREF, "").apply()
                        Timber.e("Sign in with credentials failed!")
                    }

                }
        } else {

        }
    }

    private fun logout() {
        authRepository.logout()
    }


}