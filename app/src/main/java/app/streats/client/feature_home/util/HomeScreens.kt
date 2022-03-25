package app.streats.client.feature_home.util

sealed class HomeScreens(val route: String) {
    object HomeScreen : HomeScreens("home_screen")
    object ShopScreen : HomeScreens("shop_screen")
}