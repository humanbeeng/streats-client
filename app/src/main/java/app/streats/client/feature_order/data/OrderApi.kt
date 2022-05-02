package app.streats.client.feature_order.data

import app.streats.client.feature_order.data.dto.OrderDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface OrderApi {

    @GET("/orders/initiate")
    suspend fun initiateOrder(@Header("Authorization") accessToken : String) : Response<OrderDTO>

}