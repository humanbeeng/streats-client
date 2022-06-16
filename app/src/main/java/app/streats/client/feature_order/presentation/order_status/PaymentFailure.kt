package app.streats.client.feature_order.presentation.order_status

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun OrderFailure() {
    Scaffold(modifier = Modifier.fillMaxSize(), backgroundColor = Color.Red) {
        Text(text = "Order failed")

    }
}