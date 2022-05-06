package app.streats.client.feature_cart.presentation.contracts

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import app.streats.client.feature_cart.presentation.activities.CheckoutActivity
import app.streats.client.feature_cart.util.CheckoutConstants.PARAM_CF_TOKEN
import app.streats.client.feature_cart.util.CheckoutConstants.PARAM_EMAIL
import app.streats.client.feature_cart.util.CheckoutConstants.PARAM_USERNAME
import app.streats.client.feature_order.data.dto.OrderDTO
import com.cashfree.pg.CFPaymentService.*

class CheckoutContract : ActivityResultContract<OrderDTO, Int>() {


    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        return resultCode
    }

    override fun createIntent(context: Context, order: OrderDTO): Intent {
        val checkoutIntent = Intent(context, CheckoutActivity::class.java)

        checkoutIntent.putExtra(PARAM_USERNAME, order.username)
        checkoutIntent.putExtra(PARAM_EMAIL, order.email)
        checkoutIntent.putExtra(PARAM_ORDER_ID, order.orderId)
        checkoutIntent.putExtra(PARAM_ORDER_AMOUNT, order.orderAmount)
        checkoutIntent.putExtra(PARAM_ORDER_CURRENCY, order.orderCurrency)
        checkoutIntent.putExtra(PARAM_APP_ID, order.appId)
        checkoutIntent.putExtra(PARAM_CF_TOKEN, order.cftoken)

        return checkoutIntent
    }

}
