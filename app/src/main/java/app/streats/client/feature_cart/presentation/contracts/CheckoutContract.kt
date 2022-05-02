package app.streats.client.feature_cart.presentation.contracts

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import app.streats.client.feature_cart.presentation.activities.CheckoutActivity
import app.streats.client.feature_order.data.dto.OrderDTO

class CheckoutContract : ActivityResultContract<OrderDTO, Int>() {


    override fun parseResult(resultCode: Int, intent: Intent?): Int {
        return resultCode
    }

    override fun createIntent(context: Context, order: OrderDTO): Intent {
        val checkoutIntent = Intent(context, CheckoutActivity::class.java)

        checkoutIntent.putExtra("orderId", order.orderId)
        checkoutIntent.putExtra("orderAmount", order.orderAmount)
        checkoutIntent.putExtra("orderCurrency", order.orderCurrency)
        checkoutIntent.putExtra("appId", order.appId)
        checkoutIntent.putExtra("cftoken", order.cftoken)

        return checkoutIntent
    }

}
