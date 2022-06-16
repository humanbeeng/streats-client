package app.streats.client.feature_home.presentation.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import app.streats.client.R
import app.streats.client.core.presentation.ui.theme.LightBlack

@Composable
fun SubLocalityText(subLocality: String) {
    Text(
        text = "📍 $subLocality",
        style = TextStyle(
            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
            color = LightBlack
        )
    )

}