package app.streats.client.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import app.streats.client.core.presentation.components.BottomNavItem
import app.streats.client.core.presentation.components.BottomNavigationBar
import app.streats.client.core.presentation.components.Navigation
import app.streats.client.core.presentation.ui.theme.StreatsTheme
import app.streats.client.feature_cart.util.CartScreens
import app.streats.client.feature_home.util.HomeScreens
import app.streats.client.feature_order.util.OrderScreens
import dagger.hilt.android.AndroidEntryPoint


/**
 * TODO : Resolve navigation issue of BottomNavBar
 *
 * TODO : Remove AppBar from application
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )

        setContent {
            StreatsTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val interactionSource = remember { MutableInteractionSource() }
                Navigation(
                    navController = navController
                )
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Home",
                                    route = HomeScreens.HomeScreen.route,
                                    icon = Icons.Default.Home
                                ),
                                BottomNavItem(
                                    name = "Cart",
                                    route = CartScreens.CartScreen.route,
                                    icon = Icons.Default.ShoppingCart,
                                    badgeCount = 3
                                ),
                                BottomNavItem(
                                    name = "Orders",
                                    route = OrderScreens.OrderHistoryScreen.route,
                                    icon = Icons.Default.List
                                )
                            ),
                            navController = navController,
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = interactionSource,
                                onClick = {}),
                            onItemClick = {
                                navController.navigate(it.route) {
                                    popUpTo(HomeScreens.HomeScreen.route)

                                }
                            }
                        )
                    }
                ) {
                    Navigation(navController = navController)
                }

            }
        }
    }
}


/**
 * TODO : Move to Cart & Order Package
 */


