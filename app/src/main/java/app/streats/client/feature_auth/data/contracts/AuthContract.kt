package app.streats.client.feature_auth.data.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import app.streats.client.feature_auth.util.AuthConstants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task


class AuthContract : ActivityResultContract<Int, Task<GoogleSignInAccount>?>() {
    override fun createIntent(context: Context, input: Int): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(AuthConstants.SERVER_CLIENT_ID)
            .requestEmail()
            .build()

        val client = GoogleSignIn.getClient(context, gso)


        val intent = client.signInIntent
        intent.putExtra("input", input)
        return intent


    }

    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount>? {

        return when (resultCode) {
            Activity.RESULT_OK -> {
                GoogleSignIn.getSignedInAccountFromIntent(intent)
            }
            else -> null
        }

    }

}