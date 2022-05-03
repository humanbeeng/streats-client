package app.streats.client.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.streats.client.core.presentation.screens.ErrorScreen
import app.streats.client.core.util.CoreScreens
import app.streats.client.feature_auth.presentation.login_screen.LoginScreen
import app.streats.client.feature_auth.presentation.permissions.PermissionsRationaleScreen
import app.streats.client.feature_auth.presentation.splash_screen.SplashScreen
import app.streats.client.feature_auth.util.AuthScreens
import app.streats.client.feature_cart.presentation.cart_screen.CartScreen
import app.streats.client.feature_cart.util.CartScreens
import app.streats.client.feature_home.presentation.home_screen.HomeScreen
import app.streats.client.feature_home.presentation.shop_screen.ShopScreen
import app.streats.client.feature_home.util.HomeScreens
import app.streats.client.feature_order.presentation.order_history.OrderHistoryScreen
import app.streats.client.feature_order.presentation.order_status.OrderFailure
import app.streats.client.feature_order.presentation.order_status.OrderSuccess
import app.streats.client.feature_order.util.OrderScreens

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = AuthScreens.SplashScreen.route) {

        composable(AuthScreens.SplashScreen.route) {
            SplashScreen(
                onLoggedIn = {
                    navController.navigate(HomeScreens.HomeScreen.route) {
                        while (navController.backQueue.isEmpty().not()) {
                            navController.popBackStack()
                        }
                    }
                },
                onLoggedOut = {
                    navController.navigate(AuthScreens.LoginScreen.route) {
                        while (navController.backQueue.isEmpty().not()) {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }

        composable(AuthScreens.LoginScreen.route) {
            LoginScreen(
                onLoggedIn = {
                    navController.navigate(HomeScreens.HomeScreen.route) {
                        while (navController.backQueue.isEmpty().not()) {
                            navController.popBackStack()
                        }
                    }
                },
                onLoginError = {
                    navController.navigate(CoreScreens.ErrorScreen.route) {
                        while (navController.backQueue.isEmpty().not()) {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }

        composable(AuthScreens.PermissionsRationaleScreen.route) {
            PermissionsRationaleScreen()
        }

        composable(HomeScreens.HomeScreen.route) {
            HomeScreen(
                onShopItemSelected = {
                    navController.navigate(HomeScreens.ShopScreen.route + "/${it.shopId}")
                },
                onLoggedOut = {
                    navController.navigate(AuthScreens.LoginScreen.route) {
                        while (navController.backQueue.isEmpty().not()) {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }

        composable(CartScreens.CartScreen.route) {
            CartScreen(
                onOrderFailure = { navController.navigate(OrderScreens.OrderFailureScreen.route) },
                onOrderSuccess = { navController.navigate(OrderScreens.OrderSuccessScreen.route) })
        }

        composable(OrderScreens.OrderHistoryScreen.route) {
            OrderHistoryScreen()
        }

        composable(HomeScreens.ShopScreen.route + "/{shopId}") {
            ShopScreen()
        }

        composable(CoreScreens.ErrorScreen.route) {
            ErrorScreen()
        }

        composable(route = OrderScreens.OrderSuccessScreen.route) {
            OrderSuccess()
        }

        composable(route = OrderScreens.OrderFailureScreen.route) {
            OrderFailure()
        }

    }

}