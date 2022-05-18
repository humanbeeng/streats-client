package app.streats.client.feature_auth.presentation.permissions

data class PermissionState(
    val hasAllPermissions: Boolean = false,
    val isDeniedPermanently: Boolean = false,
    val shouldShowRationale: Boolean  = false
)
