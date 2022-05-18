package app.streats.client.feature_cart.presentation.contracts

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import app.streats.client.feature_cart.presentation.activities.CheckoutActivity
import app.streats.client.feature_cart.util.CheckoutConstants.PARAM_CF_TOKEN
import app.streats.client.feature_cart.util.CheckoutConstants.PARAM_EMAIL
import app.streats.client.feature_cart.util.CheckoutConstants.PARAM_USERNAME
import app.streats.client.feature_order.data.dto.CheckoutDTO
import com.cashfree.pg.CFPaymentService.*

class CheckoutContract : ActivityResultContract<CheckoutDTO, Int>() {


    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        return resultCode
    }

    override fun createIntent(context: Context, checkout: CheckoutDTO): Intent {
        val checkoutIntent = Intent(context, CheckoutActivity::class.java)

        checkoutIntent.putExtra(PARAM_USERNAME, checkout.username)
        checkoutIntent.putExtra(PARAM_EMAIL, checkout.email)
        checkoutIntent.putExtra(PARAM_ORDER_ID, checkout.orderId)
        checkoutIntent.putExtra(PARAM_ORDER_AMOUNT, checkout.orderAmount)
        checkoutIntent.putExtra(PARAM_ORDER_CURRENCY, checkout.orderCurrency)
        checkoutIntent.putExtra(PARAM_APP_ID, checkout.appId)
        checkoutIntent.putExtra(PARAM_CF_TOKEN, checkout.cftoken)

        return checkoutIntent
    }

}
