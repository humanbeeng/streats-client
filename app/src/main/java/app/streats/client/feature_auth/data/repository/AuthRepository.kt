package app.streats.client.feature_auth.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import app.streats.client.core.util.Resource
import app.streats.client.core.util.SimpleResource
import app.streats.client.feature_auth.data.AuthApi
import app.streats.client.feature_auth.data.dto.VerifyRequest
import app.streats.client.feature_auth.util.AuthConstants
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
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
                val googleSignInToken = signInWithCredentials(credentials)

                return if (googleSignInToken.isEmpty())
                    Resource.Error(message = AuthConstants.ERROR_STRING)
                else {
                    return verifyToken(googleSignInToken)
                }

            } else {
                return Resource.Error(message = AuthConstants.ERROR_STRING)
            }


        } catch (e: Exception) {
            return Resource.Error(message = AuthConstants.ERROR_STRING)
        }


    }

    private fun signInWithCredentials(credentials: AuthCredential): String {

        FirebaseAuth.getInstance().signInWithCredential(credentials)
            .addOnCompleteListener { signInWithCredentialTask ->
                if (signInWithCredentialTask.isSuccessful) {

                    FirebaseAuth.getInstance().currentUser?.getIdToken(false)
                        ?.addOnCompleteListener { idTokenTask ->

                            if (idTokenTask.isSuccessful) {
                                sharedPreferences.edit {
                                    putString(
                                        AuthConstants.GOOGLE_SIGN_IN_TOKEN_PREF,
                                        idTokenTask.result.token
                                    ).apply()
                                }
                                Timber.d("Sign in with credentials success.")
                            } else {
                                sharedPreferences.edit()
                                    .putString(AuthConstants.GOOGLE_SIGN_IN_TOKEN_PREF, "").apply()
                                Timber.e("Sign in failed while fetching token.")


                            }
                        }


                } else {
                    sharedPreferences.edit()
                        .putString(AuthConstants.GOOGLE_SIGN_IN_TOKEN_PREF, "").apply()
                    Timber.e("Sign in with credentials failed!")
                }

            }

        return sharedPreferences.getString(AuthConstants.GOOGLE_SIGN_IN_TOKEN_PREF, "").toString();
    }

    private suspend fun verifyToken(googleSignInToken: String): SimpleResource {

        return try {
            val verifyResponse = api.verify(VerifyRequest(googleSignInToken))

            if (verifyResponse.isVerified) {
                val accessToken = verifyResponse.accessToken
                sharedPreferences
                    .edit()
                    .putString(AuthConstants.ACCESS_TOKEN_PREF, accessToken)
                    .apply()
                Resource.Success(Unit)
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