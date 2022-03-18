package app.streats.client.feature_auth.presentation.login_screen

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task


sealed class LoginEvent {
    class Login(val task: Task<GoogleSignInAccount>?) : LoginEvent()
    object Logout : LoginEvent()
}