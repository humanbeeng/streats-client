package app.streats.client.feature_auth.data.repository

import android.content.SharedPreferences
import app.streats.client.core.util.Resource
import app.streats.client.feature_auth.data.AuthApi
import app.streats.client.feature_auth.data.dto.VerifyRequest
import app.streats.client.feature_auth.util.AuthConstants
import com.google.firebase.auth.FirebaseAuth
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
) {


    suspend fun verifyToken(googleSignInToken: String): Resource<String> {

        return try {
            val verifyResponse = api.verify(VerifyRequest(googleSignInToken))

            if (verifyResponse.isVerified) {
                val accessToken = verifyResponse.accessToken
                sharedPreferences
                    .edit()
                    .putString(AuthConstants.ACCESS_TOKEN_PREF, "Bearer $accessToken")
                    .apply()
                Resource.Success(data = accessToken)
            } else {
                Timber.e(AuthConstants.AUTH_REQUEST_FAILURE)
                Resource.Error(message = AuthConstants.ERROR_STRING)
            }
        } catch (e: HttpException) {
            Timber.e(AuthConstants.AUTH_REQUEST_FAILURE)
            Resource.Error(message = AuthConstants.ERROR_STRING)
        }

    }

    fun logout() {
        firebaseAuth.signOut()
        sharedPreferences.edit().clear().apply()
    }

}