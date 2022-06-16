package app.streats.client.core.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import app.streats.client.feature_order.presentation.order_status.PaymentFailure
import app.streats.client.feature_order.presentation.order_status.PaymentSuccess
import app.streats.client.feature_order.util.OrderScreens

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier.fillMaxSize()) {

    NavHost(
        navController = navController,
        startDestination = AuthScreens.SplashScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {

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
                },
                onCartClicked = {
                    navController.navigate(CartScreens.CartScreen.route)
                }
            )
        }

        composable(CartScreens.CartScreen.route) {
            CartScreen(
                onPaymentFailure = {
                    navController.navigate(OrderScreens.OrderFailureScreen.route) {
                        popUpTo(HomeScreens.HomeScreen.route)

                    }
                },
                onPaymentSuccess = {
                    navController.navigate(OrderScreens.OrderSuccessScreen.route) {
                        popUpTo(HomeScreens.HomeScreen.route)
                    }
                },
                onGoBack = {
                    navController.popBackStack()
                })
        }

        composable(OrderScreens.OrderHistoryScreen.route) {
            OrderHistoryScreen()
        }

        composable(HomeScreens.ShopScreen.route + "/{shopId}") {
            ShopScreen {
                navController.navigate(CartScreens.CartScreen.route)
            }
        }

        composable(CoreScreens.ErrorScreen.route) {
            ErrorScreen()
        }

        composable(route = OrderScreens.OrderSuccessScreen.route) {
            PaymentSuccess(onNavigate = {
                navController.navigate(HomeScreens.HomeScreen.route)
            })
        }

        composable(route = OrderScreens.OrderFailureScreen.route) {
            PaymentFailure(onNavigate = {
                navController.navigate(HomeScreens.HomeScreen.route)
            })
        }

    }

}