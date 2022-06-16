package app.streats.client.feature_cart.presentation.cart_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.streats.client.core.presentation.ui.theme.Shapes
import app.streats.client.core.presentation.ui.theme.Tangerine

@Composable
fun AddToCartButton(onItemAdded: () -> Unit) {
    Surface(shape = Shapes.small, color = Tangerine, modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { onItemAdded() },
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(Icons.Outlined.Add, contentDescription = null)
        }

    }

}

@Composable
fun RemoveFromCartButton(onItemRemoved: () -> Unit) {

    Surface(shape = Shapes.small, color = Tangerine, modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = {
                onItemRemoved()
            },
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(Icons.Outlined.Remove, contentDescription = null)
        }

    }


}

@Composable
fun CartButtons(
    onItemAdded: () -> Unit,
    onItemRemoved: () -> Unit
) {

    Card(
        modifier = Modifier
            .height(40.dp), shape = Shapes.large,
        backgroundColor = Tangerine,
        elevation = 4.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize(), horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1f)
            ) {
                RemoveFromCartButton(onItemRemoved)
            }
            Column(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(1f)
            ) {
                AddToCartButton(onItemAdded)
            }
        }
    }
}
