package app.streats.client.feature_auth.presentation.login_screen

import android.content.SharedPreferences
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import app.streats.client.core.presentation.events.UIEvent
import app.streats.client.feature_auth.data.contracts.AuthContract
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit = {}
) {

    val loginState = loginScreenViewModel.loginState.value

    LaunchedEffect(key1 = true) {
        loginScreenViewModel.outgoingEventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    onLoggedIn()
                }
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {


        val authResultLauncher =
            rememberLauncherForActivityResult(contract = AuthContract()) { task ->
                try {
                    loginScreenViewModel.loginEventHandler(LoginEvent.Login(task))
                } catch (e: Exception) {
                    Timber.w("Exception occurred ${e.localizedMessage}")
                }

            }

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Button(onClick = { authResultLauncher.launch(1) }) {
                Text(text = "Continue with Google")

            }

            Button(onClick = { FirebaseAuth.getInstance().signOut() }) {
                Text(text = "Logout")
            }

            if (loginState.isLoading) {
                CircularProgressIndicator()
            }

            if (loginState.error.isNotEmpty()) {
                Toast.makeText(LocalContext.current, loginState.error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

