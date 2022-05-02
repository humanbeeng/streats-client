package app.streats.client.feature_auth.data.repository

import android.content.SharedPreferences
import app.streats.client.core.util.AccessToken
import app.streats.client.core.util.Constants.ERROR_MESSAGE
import app.streats.client.core.util.Resource
import app.streats.client.feature_auth.data.AuthApi
import app.streats.client.feature_auth.data.dto.AuthRequestDTO
import app.streats.client.feature_auth.data.dto.LoginRequestDTO
import app.streats.client.feature_auth.domain.models.CurrentLocationCoordinates
import app.streats.client.feature_auth.util.AuthConstants
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences,
    private val accessToken: AccessToken
) {


    /**
     * TODO : Add user location and fcmToken
     *
     * TODO : Refactor function params
     *
     * TODO : Extract error into separate Util class
     */
    fun login(
        currentLocationCoordinates: CurrentLocationCoordinates,
        fcmToken: String,
        idToken: String
    ): Flow<Resource<String>> {

        return flow {
            try {
                emit(Resource.Loading())
                val loginResponse =
                    api.login(
                        LoginRequestDTO(
                            currentLocationCoordinates, fcmToken, idToken
                        )
                    )

                if (loginResponse.isVerified) {
                    val accessTokenFromResponse = loginResponse.accessToken
                    accessToken.value = "Bearer $accessTokenFromResponse"
                    sharedPreferences
                        .edit()
                        .putString(
                            AuthConstants.ACCESS_TOKEN_PREF,
                            "Bearer $accessTokenFromResponse"
                        )
                        .apply()
                    emit(Resource.Success(data = accessTokenFromResponse))
                } else {
                    Timber.e(AuthConstants.AUTH_REQUEST_FAILURE)
                    emit(Resource.Error(message = AuthConstants.ERROR_STRING))
                }


            } catch (e: HttpException) {
                Timber.e(AuthConstants.AUTH_REQUEST_FAILURE)
                emit(Resource.Error(message = AuthConstants.ERROR_STRING))
            }
        }
    }


    fun authenticate(
        accessToken: String,
        fcmToken: String,
        currentLocationCoordinates: CurrentLocationCoordinates
    ): Flow<Resource<String>> {
        return flow {
            try {
                emit(Resource.Loading())
                val authResponse =
                    api.authenticate(
                        accessToken,
                        AuthRequestDTO(
                            currentLocationCoordinates, accessToken, fcmToken
                        )
                    )
                if (authResponse.isSuccessful) {
//                    TODO : Refactor success case
                    emit(Resource.Success(data = "Auth Success"))
                } else {
                    Timber.e("Error while authenticating ${authResponse.message()}")
                    emit(Resource.Error(message = ERROR_MESSAGE))
                }

            } catch (e: HttpException) {
                Timber.e(e.message())
                emit(Resource.Error(message = ERROR_MESSAGE))

            } catch (e: IOException) {
                Timber.e(e.localizedMessage?.toString() ?: ERROR_MESSAGE)
                emit(
                    Resource.Error(
                        message = ERROR_MESSAGE
                    )
                )
            }
        }

    }


    fun logout() {
        firebaseAuth.signOut()
        accessToken.value = ""
        sharedPreferences.edit().clear().apply()
    }

}