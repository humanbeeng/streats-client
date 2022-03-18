package app.streats.client.core.presentation.components

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.streats.client.feature_auth.presentation.login_screen.LoginScreen
import app.streats.client.feature_auth.presentation.splash_screen.SplashScreen
import app.streats.client.feature_auth.util.AuthScreens
import app.streats.client.feature_home.presentation.HomeScreen
import app.streats.client.feature_home.util.HomeScreens

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = AuthScreens.SplashScreen.route) {

        composable(AuthScreens.SplashScreen.route) {
            SplashScreen(
                onNavigate = { route ->
                    navController.popBackStack();
                    navController.navigate(route)
                }
            )
        }

        composable(AuthScreens.LoginScreen.route) {
            LoginScreen(
                onLoggedIn = {
                    navController.popBackStack(
                        route = AuthScreens.LoginScreen.route,
                        inclusive = true
                    );
                    navController.navigate(route = HomeScreens.HomeScreen.route)
                }
            )
        }

        composable(HomeScreens.HomeScreen.route) {
            HomeScreen()
        }

    }

}