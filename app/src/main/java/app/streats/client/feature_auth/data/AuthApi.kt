package app.streats.client.feature_auth.data

import app.streats.client.feature_auth.data.dto.VerifyRequest
import app.streats.client.feature_auth.data.dto.VerifyResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth")
    suspend fun verify(@Body verifyRequest: VerifyRequest): VerifyResponse

}