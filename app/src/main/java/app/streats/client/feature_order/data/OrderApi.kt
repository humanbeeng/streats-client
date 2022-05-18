package app.streats.client.feature_order.data

import app.streats.client.feature_order.data.dto.CheckoutDTO
import app.streats.client.feature_order.domain.models.Order
import com.google.common.net.HttpHeaders
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface OrderApi {

    @GET("/orders/initiate")
    suspend fun initiateOrder(
        @Header(HttpHeaders.AUTHORIZATION) accessToken: String
    ): Response<CheckoutDTO>

    @GET("/orders")
    suspend fun fetchUserOrders(
        @Header(HttpHeaders.AUTHORIZATION) accessToken: String
    ): Response<List<Order>>

}