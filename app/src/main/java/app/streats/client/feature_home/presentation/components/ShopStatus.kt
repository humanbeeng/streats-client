package app.streats.client.feature_home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.NoMeals
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.streats.client.core.presentation.ui.theme.CardCaption


@Composable
fun ShopStatus(isShopOpen: Boolean, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = modifier) {
            Icon(
                (if (isShopOpen) Icons.Outlined.Check else Icons.Outlined.NoMeals),
                contentDescription = if (isShopOpen) "Open" else "Closed",
                modifier = Modifier.size(12.dp)
            )
        }
        Column(modifier = modifier) {
            Text(text = if (isShopOpen) "Open" else "Closed", style = CardCaption)
        }
    }
}