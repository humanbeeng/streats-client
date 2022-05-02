package app.streats.client.feature_auth.presentation.permissions

data class PermissionState(
    val hasAllPermissions: Boolean = false,
    val isDeniedPermanently: Boolean? = null,
    val shouldShowRationale: Boolean? = null
)
