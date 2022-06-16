package app.streats.client.feature_cart.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BadgedBox
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.streats.client.R
import app.streats.client.core.presentation.ui.theme.LightBlack
import app.streats.client.core.presentation.ui.theme.Shapes


@Composable
fun CartFloatingButton(onCartClicked: () -> Unit, itemCount: Int = 0) {
    FloatingActionButton(
        onClick = { onCartClicked() },
        modifier = Modifier
            .padding(bottom = 45.dp)
            .size(65.dp),
        shape = Shapes.large,
        contentColor = LightBlack
    ) {
        BadgedBox(badge = {
//            Text(
//                text = itemCount.toString(),
//                color = Tangerine,
//                modifier = Modifier.padding(2.dp)
//            )
        }) {
            Icon(painter = painterResource(id = R.drawable.bag), contentDescription = "Cart Button")
        }
    }
}