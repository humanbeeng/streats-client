package app.streats.client.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


/**
 * TODO : Add Proper padding to content
 */

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    BottomAppBar(
        elevation = 20.dp,
        cutoutShape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White,
        contentPadding = PaddingValues(horizontal = 40.dp)
    ) {

        items.forEach { item ->
            IconButton(onClick = { onItemClick(item) }) {
                Icon(imageVector = item.icon, contentDescription = item.name)
            }
        }


    }
}



