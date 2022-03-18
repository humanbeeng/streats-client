package app.streats.client.feature_auth.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import app.streats.client.core.util.Resource
import app.streats.client.core.util.SimpleResource
import app.streats.client.feature_auth.data.AuthApi
import app.streats.client.feature_auth.data.dto.VerifyRequest
import app.streats.client.feature_auth.presentation.login_screen.LoginState
import app.streats.client.feature_auth.util.AuthConstants
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
) {


    suspend fun loginWithGoogle(task: Task<GoogleSignInAccount>?): SimpleResource {

        try {
            val googleSignedInAccount = task?.getResult(ApiException::class.java)

            if (googleSignedInAccount != null) {
                Timber.d("gsa not null")
                val idToken = task.result.idToken!!
                val credentials = GoogleAuthProvider.getCredential(idToken, null)

                FirebaseAuth.getInstance().signInWithCredential(credentials)
                    .addOnCompleteListener { signInWithCredentialTask ->
                        if (signInWithCredentialTask.isSuccessful) {
                            Timber.d("Login success")
                            val userIdToken =
                                FirebaseAuth.getInstance().currentUser?.getIdToken(false)
                                    ?.addOnCompleteListener { idTokenTask ->
                                        if (idTokenTask.isSuccessful) {
                                            sharedPreferences.edit {
                                                putString(
                                                    AuthConstants.GOOGLE_SIGN_IN_TOKEN_STRING,
                                                    idTokenTask.result.token
                                                ).apply()
                                            }
                                        }
                                    }

                            Timber.d("Token : ${userIdToken?.result?.token}")

                        } else {
                            sharedPreferences.edit()
                                .putString(AuthConstants.GOOGLE_SIGN_IN_TOKEN_STRING, "").apply()
                            Timber.d("Login failed")
                        }
                    }


            }

            val googleSignInToken =
                sharedPreferences.getString(AuthConstants.GOOGLE_SIGN_IN_TOKEN_STRING, "")!!


            return if (googleSignInToken.isEmpty())
                Resource.Error(message = AuthConstants.ERROR_STRING)
            else {
                return try {
                    val verifyResponse = api.verify(VerifyRequest(googleSignInToken))

                    if (verifyResponse.isVerified) {
                        val accessToken = verifyResponse.accessToken
                        sharedPreferences.edit().putString("AccessToken", accessToken).apply()
                        Timber.d( sharedPreferences.getString("AccessToken", ""))
                        Resource.Success(Unit)
                    } else {
                        Timber.e(AuthConstants.ERROR_STRING)
                        Resource.Error(message = AuthConstants.ERROR_STRING)
                    }
                } catch (e: HttpException) {
                    Timber.e(AuthConstants.ERROR_STRING)
                    Resource.Error(message = AuthConstants.ERROR_STRING)
                }
            }

        } catch (e: Exception) {
            return Resource.Error(message = AuthConstants.ERROR_STRING)
        }


    }


    private suspend fun verifyToken(googleSignInToken: String): SimpleResource {

        return try {
            val verifyResponse = api.verify(VerifyRequest(googleSignInToken))

            if (verifyResponse.isVerified) {
                val accessToken = verifyResponse.accessToken
                sharedPreferences.edit().putString("AccessToken", accessToken).apply()
                Resource.Success(Unit)
            } else {
                Timber.e(AuthConstants.ERROR_STRING)
                Resource.Error(message = AuthConstants.ERROR_STRING)
            }
        } catch (e: HttpException) {
            Timber.e(AuthConstants.ERROR_STRING)
            Resource.Error(message = AuthConstants.ERROR_STRING)
        }

    }
}