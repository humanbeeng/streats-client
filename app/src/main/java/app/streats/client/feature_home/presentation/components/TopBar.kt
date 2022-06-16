package app.streats.client.feature_home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.streats.client.core.presentation.ui.theme.Shapes
import app.streats.client.feature_home.presentation.home_screen.MenuButton


@Composable
fun TopBar(onCartClicked: () -> Unit, currentLocation: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 50.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Surface(shape = Shapes.small, color = Color.LightGray) {
            Image(Icons.Outlined.Person, modifier = Modifier.size(32.dp), contentDescription = "User")
        }

        SubLocalityText(currentLocation)

//        TODO : Change this to menu clicked
        MenuButton(onMenuClicked = onCartClicked)

    }
}
