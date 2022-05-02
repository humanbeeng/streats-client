package app.streats.client.feature_auth.presentation.splash_screen

sealed class SplashScreenEvent {

    object AuthSuccess : SplashScreenEvent()

    object AuthFailed: SplashScreenEvent()
}
