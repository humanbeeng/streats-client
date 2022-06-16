package app.streats.client.feature_auth.presentation.login_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.streats.client.R
import app.streats.client.core.presentation.events.UIEvent
import app.streats.client.core.presentation.ui.theme.*
import app.streats.client.feature_auth.data.contracts.AuthContract
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit,
    onLoginError: () -> Unit
) {

    val loginState = loginScreenViewModel.loginState.value

    val isLoading = remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = true) {
        loginScreenViewModel.outgoingEventFlow.collectLatest { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    onLoggedIn();
                    isLoading.value = false
                }
                is UIEvent.Error -> onLoginError()
                is UIEvent.Loading -> {
                    isLoading.value = true
                }
            }
        }
    }


    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthContract()) { task ->
            try {
                loginScreenViewModel.loginEventHandler(LoginEvent.Login(task))
            } catch (e: Exception) {
                onLoginError()
                Timber.e("Exception occurred ${e.localizedMessage}")
            }

        }

    LoginScreenUI(onLogin = { authResultLauncher.launch(1) })
}


@Composable
fun LoginScreenUI(onLogin: () -> Unit) {


    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(5f)
            ) {
                LoginScreenAnimation()
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f)
            ) {
                LoginCard(onLogin = onLogin)
            }
        }
    }
}

@Composable
fun LoginScreenAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.login_screen)
    )
    val progress by animateLottieCompositionAsState(
        // pass the composition created above
        composition,

        // Iterates Forever
        iterations = LottieConstants.IterateForever,

        // pass isPlaying we created above,
        // changing isPlaying will recompose
        // Lottie and pause/play


        // this makes animation to restart when paused and play
        // pass false to continue the animation at which is was paused
        restartOnPlay = false

    )
    LottieAnimation(
        composition,
        progress = progress,
        modifier = Modifier
            .fillMaxSize(), contentScale = ContentScale.FillWidth
    )
}

@Composable
fun LoginCard(onLogin: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp), elevation = 20.dp,
        backgroundColor = CardBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            LoginText()

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    LoginCaption()
                    LoginButton(onLogin)
                }
            }
        }
    }
}

@Composable
fun LoginCaption() {
    Text(
        text = "We have amazing food waiting for you :)",
        style = ButtonCaption,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}


@Composable
fun LoginText() {
    Text(text = "Login .", style = Typography.h3)
}

@Composable
fun LoginButton(onLogin: () -> Unit) {
    Button(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Tangerine,
            contentColor = LightBlack
        ),
        onClick = { onLogin() }) {
        Text(text = "CONTINUE WITH GOOGLE", style = Typography.button)

    }
}



