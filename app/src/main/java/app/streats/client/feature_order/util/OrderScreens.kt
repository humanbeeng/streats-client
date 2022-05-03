package app.streats.client.feature_order.util

sealed class OrderScreens(val route: String) {

    object OrderSuccessScreen : OrderScreens("order_success_screen")

    object OrderFailureScreen : OrderScreens("order_failure_screen")

    object OrderHistoryScreen : OrderScreens("order_history_screen")
}
