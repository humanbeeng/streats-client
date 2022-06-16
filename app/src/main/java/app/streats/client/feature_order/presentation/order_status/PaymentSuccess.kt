package app.streats.client.feature_order.presentation.order_status

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.streats.client.R
import app.streats.client.core.presentation.ui.theme.LightGrayBackground
import app.streats.client.core.presentation.ui.theme.OrderConfirmationText
import app.streats.client.core.presentation.ui.theme.SectionHeading
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.delay

@Composable
fun PaymentSuccess(onNavigate: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize(), backgroundColor = LightGrayBackground) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val isPlaying by remember {
                mutableStateOf(true)
            }

            val speed by remember {
                mutableStateOf(1f)
            }

//            Wait for 10s and navigate back to HomeScreen
            LaunchedEffect(key1 = Unit) {
                delay(10000)
                onNavigate()
            }

            // remember lottie composition ,which
            // accepts the lottie composition result
            val composition by rememberLottieComposition(
                LottieCompositionSpec
                    .RawRes(R.raw.payment_success)
            )
            // to control the animation
            val progress by animateLottieCompositionAsState(
                // pass the composition created above
                composition,

                // Iterates Forever
                iterations = LottieConstants.IterateForever,

                // pass isPlaying we created above,
                // changing isPlaying will recompose
                // Lottie and pause/play
                isPlaying = isPlaying,

                speed = speed,

                // this makes animation to restart when paused and play
                // pass false to continue the animation at which is was paused
                restartOnPlay = false

            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                LottieAnimation(
                    composition,
                    progress,
                    modifier = Modifier
                        .size(200.dp)
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Payment successful .", style = SectionHeading)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "You will be receiving order confirmation notification .", style = OrderConfirmationText)
            }

        }

    }
}