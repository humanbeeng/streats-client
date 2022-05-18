package app.streats.client.feature_auth.presentation.login_screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.streats.client.core.presentation.events.UIEvent
import app.streats.client.feature_auth.data.contracts.AuthContract
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit,
    onLoginError: () -> Unit
) {

    val loginState = loginScreenViewModel.loginState.value

    LaunchedEffect(key1 = true) {
        loginScreenViewModel.outgoingEventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.Navigate -> onLoggedIn()
                is UIEvent.Error -> onLoginError()
                is UIEvent.Loading -> {}
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {


        val authResultLauncher =
            rememberLauncherForActivityResult(contract = AuthContract()) { task ->
                try {
                    loginScreenViewModel.loginEventHandler(LoginEvent.Login(task))
                } catch (e: Exception) {
                    onLoginError()
                    Timber.e("Exception occurred ${e.localizedMessage}")
                }

            }

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Button(modifier = Modifier.shadow(4.dp), onClick = { authResultLauncher.launch(1) }) {
                Text(text = "Continue with Google")

            }


            if (loginState.isLoading) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .shadow(4.dp)
                        .shimmer()
                ) {

                }
            }

            if (loginState.error.isNotEmpty()) {
                Toast.makeText(LocalContext.current, loginState.error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}


