package app.streats.client.feature_auth.data.repository

import android.content.SharedPreferences
import app.streats.client.core.domain.models.AccessToken
import app.streats.client.core.util.Constants.EMPTY
import app.streats.client.core.util.Constants.ERROR_MESSAGE
import app.streats.client.core.util.Resource
import app.streats.client.feature_auth.data.AuthApi
import app.streats.client.feature_auth.data.dto.AuthRequestDTO
import app.streats.client.feature_auth.data.dto.LoginRequestDTO
import app.streats.client.feature_auth.domain.models.CurrentLocationCoordinates
import app.streats.client.feature_auth.util.AuthConstants
import app.streats.client.feature_auth.util.AuthConstants.AUTH_REQUEST_FAILURE
import app.streats.client.feature_auth.util.AuthConstants.AUTH_REQUEST_SUCCESS
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


    fun login(
        currentLocationCoordinates: CurrentLocationCoordinates,
        fcmToken: String,
        idToken: String
    ): Flow<Resource<String>> {

        return flow {
            try {
                emit(Resource.Loading())

                val loginResponse =
                    api.login(LoginRequestDTO(currentLocationCoordinates, fcmToken, idToken))

                if (loginResponse.isSuccessful) {
                    loginResponse.body()?.let {
                        if (it.isVerified) {
                            val accessTokenFromResponse = it.accessToken
                            accessToken.setAccessToken(accessTokenFromResponse)
                            sharedPreferences
                                .edit()
                                .putString(
                                    AuthConstants.ACCESS_TOKEN_PREF,
                                    accessToken.value
                                ).apply()

                            emit(Resource.Success(data = accessTokenFromResponse))
                        } else {
                            Timber.e("$AUTH_REQUEST_FAILURE ${loginResponse.message()}")
                            emit(Resource.Error(message = AuthConstants.ERROR_STRING))
                        }
                    }

                } else {
                    Timber.e(loginResponse.message())
                    emit(Resource.Error(message = AuthConstants.ERROR_STRING))
                }

            } catch (e: HttpException) {
                Timber.e("${e.localizedMessage} ${e.message()}")
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
                        AuthRequestDTO(currentLocationCoordinates, accessToken, fcmToken)
                    )

                if (authResponse.isSuccessful) {
                    emit(Resource.Success(data = AUTH_REQUEST_SUCCESS))
                } else {
                    Timber.e("$AUTH_REQUEST_FAILURE ${authResponse.message()}")
                    emit(Resource.Error(message = ERROR_MESSAGE))
                }

            } catch (e: HttpException) {
                Timber.e(e.message())
                emit(Resource.Error(message = ERROR_MESSAGE))

            } catch (e: IOException) {
                Timber.e(e.localizedMessage?.toString() ?: ERROR_MESSAGE)
                emit(Resource.Error(message = ERROR_MESSAGE))
            }
        }

    }


    fun logout() {
        firebaseAuth.signOut()
        accessToken.value = EMPTY
        sharedPreferences.edit().clear().apply()
    }

}