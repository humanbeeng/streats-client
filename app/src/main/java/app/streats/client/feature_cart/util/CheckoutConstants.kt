package app.streats.client.feature_cart.util

import app.streats.client.core.util.CoreConstants

object CheckoutConstants {
    const val PARAM_TRANSACTION_STATUS = "txStatus"
    const val PARAM_CF_TOKEN = "cftoken"
    const val PARAM_USERNAME = "username"
    const val PARAM_EMAIL = "email"
    const val PAYMENT_CALLBACK_URL = "${CoreConstants.SERVER_BASE_URL}orders/callback"
}