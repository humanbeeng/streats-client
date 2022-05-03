package app.streats.client.feature_auth.data

import app.streats.client.feature_auth.data.dto.AuthRequestDTO
import app.streats.client.feature_auth.data.dto.LoginRequestDTO
import app.streats.client.feature_auth.data.dto.LoginResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {


    @POST("/auth")
    suspend fun authenticate(
        @Header("Authorization") accessToken: String,
        @Body authRequestDTO: AuthRequestDTO
    ): Response<Unit>


    @POST("/auth/login")
    suspend fun login(
        @Body loginRequestDTO: LoginRequestDTO
    ): Response<LoginResponseDTO>


}