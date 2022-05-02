package app.streats.client.feature_cart.presentation.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import app.streats.client.feature_cart.util.PaymentConstants
import app.streats.client.feature_order.data.dto.OrderDTO
import com.cashfree.pg.CFPaymentService.*
import timber.log.Timber


class CheckoutActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("CheckoutActivity called ${intent.getStringExtra("orderId")}")
        val orderId = intent.getStringExtra("orderId")
        val orderAmount = intent.getStringExtra("orderAmount")
        val orderCurrency = intent.getStringExtra("orderCurrency")
        val appId = intent.getStringExtra("appId")
        val cftoken = intent.getStringExtra("cftoken")
        Timber.d("token : $cftoken")
        val order = OrderDTO(
            orderId = orderId!!,
            orderAmount = orderAmount!!,
            orderCurrency = orderCurrency!!,
            appId = appId!!,
            stage = "TEST",
            status = PaymentConstants.PENDING.name,
            cftoken = cftoken!!
        )

        startPayment(order)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        val bundle: Bundle? = data.extras
        if (bundle != null) {

            for (key in bundle.keySet()) {
                if (bundle.getString(key) != null) {
                    Timber.d("$key : ${bundle.getString(key)}")
                }
            }
            if (bundle["txStatus"] == PaymentConstants.SUCCESS.name) {
                super.setResult(RESULT_OK)
            } else super.setResult(RESULT_CANCELED)

        }
        super.onDestroy()
        super.finish()

    }


    private fun startPayment(order: OrderDTO) {
        val cfInstance = getCFPaymentServiceInstance()
        cfInstance.setOrientation(0)
        val paymentObject = HashMap<String, String>()

//        TODO : Replace with actual data
        paymentObject[PARAM_APP_ID] = order.appId
        paymentObject[PARAM_ORDER_ID] = order.orderId
        paymentObject[PARAM_ORDER_CURRENCY] = order.orderCurrency
        paymentObject[PARAM_ORDER_AMOUNT] = order.orderAmount
        paymentObject[PARAM_CUSTOMER_PHONE] = "9999999999"
        paymentObject[PARAM_CUSTOMER_NAME] = "TEST_CUSTOMER_1"
        paymentObject[PARAM_CUSTOMER_EMAIL] = "test@test.com"
        paymentObject[PARAM_NOTIFY_URL] =
            "https://74f3-171-76-95-176.ngrok.io/orders/callback"
//
//
        cfInstance.upiPayment(this, paymentObject, order.cftoken, order.stage)


    }


}
