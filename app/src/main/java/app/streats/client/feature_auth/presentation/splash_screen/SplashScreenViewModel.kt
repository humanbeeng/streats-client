package app.streats.client.feature_auth.presentation.splash_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import app.streats.client.feature_auth.presentation.login_screen.LoginState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    ViewModel() {

    private val _isLoggedIn = mutableStateOf(LoginState())
    val isLoggedIn: State<LoginState> = _isLoggedIn


    init {
        if (firebaseAuth.currentUser == null) {
            _isLoggedIn.value = LoginState(isLoggedIn = false, isLoading = false)
        } else {
            _isLoggedIn.value = LoginState(isLoggedIn = true, isLoading = false)
        }
    }


}