package app.streats.client.feature_auth.presentation.splash_screen

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import app.streats.client.feature_auth.presentation.permissions.PermissionState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    splashScreenViewModel: SplashScreenViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit,
    onLoggedOut: () -> Unit,
) {

    val permissionState = splashScreenViewModel.permissionState.value
    val currentLocationState = splashScreenViewModel.currentLocationState.value
    val tokenState = splashScreenViewModel.fcmTokenState.value
    val loginState = splashScreenViewModel.loginState.value

    val outgoingSplashScreenEventUIEventFlow = splashScreenViewModel.outgoingSplashScreenEventFlow
    val context = LocalContext.current

//    Request for permissions
    PermissionsUI(splashScreenViewModel)

//    Add a subscriber for incoming SplashScreenUIEvents
    LaunchedEffect(key1 = Unit) {
        outgoingSplashScreenEventUIEventFlow.collectLatest { event ->
            when (event) {
                is SplashScreenEvent.AuthSuccess -> onLoggedIn()
                is SplashScreenEvent.AuthFailed -> onLoggedOut()
            }
        }

    }

    LaunchedEffect(key1 = Unit) {
        splashScreenViewModel.splashScreenEventHandler(SplashScreenRequest.LaunchSplash(context = context))

    }

    LaunchedEffect(key1 = permissionState.hasAllPermissions, block = {
        if (permissionState.hasAllPermissions) {
            splashScreenViewModel.splashScreenEventHandler(SplashScreenRequest.FetchLocation(context))
        }
    })

    LaunchedEffect(key1 = (currentLocationState.isSuccessful && tokenState.isSuccessful && permissionState.hasAllPermissions)) {
        if (loginState.isLoggedIn && currentLocationState.isSuccessful && tokenState.isSuccessful && permissionState.hasAllPermissions) {
            splashScreenViewModel.splashScreenEventHandler(SplashScreenRequest.Authenticate)
        } else if (loginState.isLoggedIn.not() && currentLocationState.isSuccessful && tokenState.isSuccessful && permissionState.hasAllPermissions) {
            onLoggedOut()
        }
    }


//  TODO : Move this to different Composable
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("SplashScreen")

        }

    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsUI(
    splashScreenViewModel: SplashScreenViewModel
) {


    val permissionState =
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )


    LaunchedEffect(
        key1 = permissionState.allPermissionsGranted,
        key2 = permissionState.shouldShowRationale,
        key3 = permissionState.permissionRequested,
    ) {
        if (permissionState.allPermissionsGranted) {
            splashScreenViewModel.splashScreenEventHandler(
                SplashScreenRequest.UpdatePermissionsState(
                    PermissionState(
                        hasAllPermissions = true,
                        isDeniedPermanently = false,
                        shouldShowRationale = false
                    )
                )
            )

        }

        if (permissionState.shouldShowRationale) {
            splashScreenViewModel.splashScreenEventHandler(
                SplashScreenRequest.UpdatePermissionsState(
                    PermissionState(
                        hasAllPermissions = false,
                        isDeniedPermanently = false,
                        shouldShowRationale = true
                    )
                )
            )
        }

        if (permissionState.allPermissionsGranted.not() && permissionState.shouldShowRationale.not()) {
            splashScreenViewModel.splashScreenEventHandler(
                SplashScreenRequest.UpdatePermissionsState(
                    PermissionState(
                        hasAllPermissions = false,
                        isDeniedPermanently = true,
                        shouldShowRationale = false
                    )
                )
            )
        }
    }

//    Launch permissions dialogue ON_START of Activity
    val lifecycleOwner = LocalLifecycleOwner.current


    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                permissionState.launchMultiplePermissionRequest()
            }

        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


}

