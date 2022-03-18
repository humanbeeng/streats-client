package app.streats.client.feature_auth.presentation.splash_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import app.streats.client.feature_auth.util.AuthScreens
import app.streats.client.feature_home.util.HomeScreens

@Composable
fun SplashScreen(
    splashScreenViewModel: SplashScreenViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {

    val loginState = splashScreenViewModel.isLoggedIn

    LaunchedEffect(key1 = true) {
        when (loginState.value.isLoggedIn) {
            true -> onNavigate(HomeScreens.HomeScreen.route)
            false -> onNavigate(AuthScreens.LoginScreen.route)
        }
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("SplashScreen")

        }

    }
}