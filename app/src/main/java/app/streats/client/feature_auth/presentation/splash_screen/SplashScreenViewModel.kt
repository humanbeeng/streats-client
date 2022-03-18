package app.streats.client.feature_auth.presentation.splash_screen

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import app.streats.client.feature_auth.presentation.login_screen.LoginState
import app.streats.client.feature_auth.util.AuthConstants
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    firebaseAuth: FirebaseAuth,
    sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _isLoggedIn = mutableStateOf(LoginState())
    val isLoggedIn: State<LoginState> = _isLoggedIn


    init {
        if (isUserLoggedIn(firebaseAuth, sharedPreferences)) {
            _isLoggedIn.value = LoginState(isLoggedIn = true, isLoading = false)
        } else {
            _isLoggedIn.value = LoginState(isLoggedIn = false, isLoading = false)
        }
    }

    private fun isUserLoggedIn(
        firebaseAuth: FirebaseAuth,
        sharedPreferences: SharedPreferences
    ): Boolean {

        return (firebaseAuth.currentUser != null && sharedPreferences.getString(
            AuthConstants.ACCESS_TOKEN_PREF,
            ""
        ).isNullOrBlank().not())

    }

}