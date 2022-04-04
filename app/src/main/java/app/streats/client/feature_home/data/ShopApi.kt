package app.streats.client.feature_home.data

import app.streats.client.feature_home.data.dto.ShopDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ShopApi {

    @GET("/home/{shopId}")
    suspend fun getShopByShopId(
        @Header("Authorization") accessToken: String,
        @Path("shopId") shopId: String
    ): ShopDTO
}