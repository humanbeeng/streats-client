package app.streats.client.feature_cart.presentation.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import app.streats.client.core.util.CoreConstants.CASHFREE_STAGE_TEST
import app.streats.client.core.util.CoreConstants.DUMMY_PHONE_NUMBER
import app.streats.client.feature_cart.util.CheckoutConstants.PARAM_CF_TOKEN
import app.streats.client.feature_cart.util.CheckoutConstants.PARAM_EMAIL
import app.streats.client.feature_cart.util.CheckoutConstants.PARAM_TRANSACTION_STATUS
import app.streats.client.feature_cart.util.CheckoutConstants.PARAM_USERNAME
import app.streats.client.feature_cart.util.CheckoutConstants.PAYMENT_CALLBACK_URL
import app.streats.client.feature_cart.util.PaymentEnums
import app.streats.client.feature_order.data.dto.CheckoutDTO
import com.cashfree.pg.CFPaymentService.*
import timber.log.Timber


class CheckoutActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("CheckoutActivity called ${intent.getStringExtra("orderId")}")
        val orderId = intent.getStringExtra(PARAM_ORDER_ID)
        val orderAmount = intent.getStringExtra(PARAM_ORDER_AMOUNT)
        val orderCurrency = intent.getStringExtra(PARAM_ORDER_CURRENCY)
        val appId = intent.getStringExtra(PARAM_APP_ID)
        val cftoken = intent.getStringExtra(PARAM_CF_TOKEN)
        val username = intent.getStringExtra(PARAM_USERNAME)
        val email = intent.getStringExtra(PARAM_EMAIL)

        Timber.d("token : $cftoken")

        val order = CheckoutDTO(
            username = username!!,
            email = email!!,
            orderId = orderId!!,
            orderAmount = orderAmount!!,
            orderCurrency = orderCurrency!!,
            appId = appId!!,
            stage = CASHFREE_STAGE_TEST,
            status = PaymentEnums.PENDING.name,
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
            if (bundle[PARAM_TRANSACTION_STATUS] == PaymentEnums.SUCCESS.name) {
                super.setResult(RESULT_OK)
            } else super.setResult(RESULT_CANCELED)

        }
        super.onDestroy()
        super.finish()

    }


    private fun startPayment(checkout: CheckoutDTO) {
        val cfInstance = getCFPaymentServiceInstance()
        cfInstance.setOrientation(0)
        val paymentObject = HashMap<String, String>()

// TODO : Replace dummy phone number
        paymentObject[PARAM_APP_ID] = checkout.appId
        paymentObject[PARAM_ORDER_ID] = checkout.orderId
        paymentObject[PARAM_ORDER_CURRENCY] = checkout.orderCurrency
        paymentObject[PARAM_ORDER_AMOUNT] = checkout.orderAmount
        paymentObject[PARAM_CUSTOMER_PHONE] = DUMMY_PHONE_NUMBER
        paymentObject[PARAM_CUSTOMER_NAME] = checkout.username
        paymentObject[PARAM_CUSTOMER_EMAIL] = checkout.email
        paymentObject[PARAM_NOTIFY_URL] = PAYMENT_CALLBACK_URL
//
//
        cfInstance.upiPayment(this, paymentObject, checkout.cftoken, checkout.stage)


    }


}
