package app.streats.client.feature_auth.presentation.splash_screen

import android.content.Context
import app.streats.client.feature_auth.presentation.permissions.PermissionState

sealed class SplashScreenRequest {

    class UpdatePermissionsState(val permissionState: PermissionState) : SplashScreenRequest()

    class LaunchSplash(val context: Context) : SplashScreenRequest()

    object Authenticate : SplashScreenRequest()

    class FetchLocation(val context: Context): SplashScreenRequest()

}
