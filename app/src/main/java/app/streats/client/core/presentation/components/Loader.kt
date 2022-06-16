package app.streats.client.core.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import app.streats.client.R
import app.streats.client.core.presentation.ui.theme.LightGrayBackground
import com.airbnb.lottie.compose.*


@Composable
@Preview
fun Loader(backgroundColor: Color = LightGrayBackground.copy(alpha = 0.8f)) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(20f), backgroundColor = backgroundColor
    ) {
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


            // remember lottie composition ,which
            // accepts the lottie composition result
            val composition by rememberLottieComposition(
                LottieCompositionSpec
                    .RawRes(R.raw.loader)
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


            LottieAnimation(
                composition,
                progress,
                modifier = Modifier
                    .size(200.dp)
                    .padding(10.dp)
            )
        }
    }
}
