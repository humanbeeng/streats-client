package app.streats.client.feature_home.data

import app.streats.client.feature_home.data.dto.HomeDTO
import retrofit2.http.GET
import retrofit2.http.Header


interface HomeApi {

    @GET("/home")
    suspend fun home(@Header("Authorization") accessToken: String): HomeDTO
}