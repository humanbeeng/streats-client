package app.streats.client.feature_auth.presentation.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import app.streats.client.feature_auth.util.AuthConstants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class GoogleSignInActivity : Activity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(AuthConstants.SERVER_CLIENT_ID)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()
    }


    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        currentUser?.getIdToken(false)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Id token is ${task.result.token}")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credentials = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credentials).addOnCompleteListener(this) { signInTask ->
            if (signInTask.isSuccessful) {
                Log.d(TAG, "Sign in with credentials success")
                val user = auth.currentUser

//            Add call to backend to verify

//                Log.d(TAG, "Token ${user?.getIdToken(false)}")
            } else {
                Log.w(TAG, "Sign in with credentials failed")

            }

        }
    }


    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val TAG = "GoogleSignInActivity"
    }


}