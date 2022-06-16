package app.streats.client.feature_home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.streats.client.core.presentation.ui.theme.CardCaption

@Composable
fun ShopLocation(shopLocation: String, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = modifier) {
            Icon(
                Icons.Default.PinDrop,
                contentDescription = shopLocation,
                modifier = Modifier.size(12.dp)
            )
        }
        Column(modifier = modifier) {
            Text(text = shopLocation, style = CardCaption)
        }
    }
}