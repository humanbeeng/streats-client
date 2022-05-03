package app.streats.client.feature_auth.presentation.splash_screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.streats.client.core.domain.models.AccessToken
import app.streats.client.core.domain.models.FCMToken
import app.streats.client.core.util.Constants.EMPTY
import app.streats.client.core.util.Constants.ERROR_MESSAGE
import app.streats.client.core.util.Constants.FCM_TOKEN_PREF
import app.streats.client.core.util.Resource
import app.streats.client.feature_auth.data.repository.AuthRepository
import app.streats.client.feature_auth.domain.models.CurrentLocationCoordinates
import app.streats.client.feature_auth.presentation.login_screen.LoginState
import app.streats.client.feature_auth.presentation.permissions.PermissionState
import app.streats.client.feature_auth.util.AuthConstants
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val sharedPreferences: SharedPreferences,
    private val accessToken: AccessToken,
    private val authRepository: AuthRepository,
    private val firebaseMessaging: FirebaseMessaging,
    private val currentLocationCoordinates: CurrentLocationCoordinates,
    private val fcmToken: FCMToken
) : ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _fcmTokenState = mutableStateOf(FcmTokenState())
    val fcmTokenState: State<FcmTokenState> = _fcmTokenState

    private val _currentLocationState = mutableStateOf(CurrentLocationState())
    val currentLocationState: State<CurrentLocationState> = _currentLocationState

    private val _permissionState = mutableStateOf(PermissionState())
    val permissionState: State<PermissionState> = _permissionState

    private val _outgoingSplashScreenEventFlow = MutableSharedFlow<SplashScreenEvent>()
    val outgoingSplashScreenEventFlow = _outgoingSplashScreenEventFlow.asSharedFlow()

    private val _isLaunchSplashCalled = mutableStateOf(false)

    private val _isAuthCalled = mutableStateOf(false)


    fun splashScreenEventHandler(splashScreenRequest: SplashScreenRequest) {
        when (splashScreenRequest) {
            is SplashScreenRequest.UpdatePermissionsState -> {
                _permissionState.value = splashScreenRequest.permissionState
                Timber.d("Permissions updated ${_permissionState.value}")
            }

            is SplashScreenRequest.LaunchSplash -> {
                if (_isLaunchSplashCalled.value.not()) {
                    _isLaunchSplashCalled.value = true
                    retrieveFcmToken()
                    updateUserLoggedInState()

                }

            }

            is SplashScreenRequest.FetchLocation -> retrieveCurrentLocation(splashScreenRequest.context)


            is SplashScreenRequest.Authenticate -> {
                if (_isAuthCalled.value.not()) {
                    _isAuthCalled.value = true
                    authRepository.authenticate(
                        accessToken.value,
                        currentLocationCoordinates = currentLocationCoordinates,
                        fcmToken = _fcmTokenState.value.fcmToken
                    ).onEach { requestState ->
                        when (requestState) {
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                _outgoingSplashScreenEventFlow.emit(SplashScreenEvent.AuthSuccess)
                            }

                            is Resource.Error -> {
                                _outgoingSplashScreenEventFlow
                                    .emit(SplashScreenEvent.AuthFailed)

                            }

                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }


    private fun allPermissionsGranted(): Boolean {
        return _permissionState.value.hasAllPermissions
    }

    //    TODO : Fix error retrieving currentLocation Exception
    @SuppressLint("MissingPermission")
    private fun retrieveCurrentLocation(context: Context) {
        Timber.d("Retrieve Current Location called")

        if (allPermissionsGranted()) {
            val fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)

            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { currentLocationTask ->
                    if (currentLocationTask != null) {
                        _currentLocationState.value =
                            CurrentLocationState(
                                currentLocationCoordinates = CurrentLocationCoordinates(
                                    currentLocationTask.latitude,
                                    currentLocationTask.longitude
                                ),
                                isLoading = false,
                                isSuccessful = true
                            )

                        currentLocationCoordinates.latitude = currentLocationTask.latitude
                        currentLocationCoordinates.longitude = currentLocationTask.longitude
                    }
                    Timber.d("${_currentLocationState.value.currentLocationCoordinates}")


                }.addOnFailureListener { currentLocationFailure ->
                    throw RuntimeException(currentLocationFailure.localizedMessage)
                }
        }

    }

    private fun isUserLoggedIn(): Boolean {
        return (firebaseAuth.currentUser != null && sharedPreferences.getString(
            AuthConstants.ACCESS_TOKEN_PREF,
            EMPTY
        ).isNullOrBlank().not())
    }


    private fun updateUserLoggedInState() {
        if (isUserLoggedIn()) {
            _loginState.value = LoginState(isLoggedIn = true, isLoading = false)
        } else {
            _loginState.value = LoginState(isLoggedIn = false, isLoading = false)
        }
    }


    private fun retrieveFcmToken() {
        try {
            firebaseMessaging.token.addOnSuccessListener { tokenTask ->
                _fcmTokenState.value =
                    FcmTokenState(fcmToken = tokenTask.toString(), isSuccessful = true)

                sharedPreferences.edit().putString(FCM_TOKEN_PREF, tokenTask).apply()
                fcmToken.value = tokenTask.toString()
            }
        } catch (e: Exception) {
            _fcmTokenState.value = FcmTokenState(error = ERROR_MESSAGE, isSuccessful = false)
            fcmToken.value = EMPTY
        }
    }
}
