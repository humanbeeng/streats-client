package app.streats.client.feature_cart.data

import app.streats.client.feature_cart.data.dto.CartDTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CartApi {

    @GET("/cart")
    suspend fun cart(@Header("Authorization") accessToken: String): CartDTO

    @POST("/cart")
    suspend fun addToCart(@Header("Authorization") accessToken: String): CartDTO

    @DELETE("/cart")
    suspend fun removeFromCart(@Header("Authorization") accessToken: String): CartDTO
}