package app.streats.client.feature_auth.presentation.permissions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * TODO : Add Button to request permissions after showing rationale
 * TODO : Refactor UI
 */

@Composable
fun PermissionsRationaleScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) {

        Text(text = "We need to know your location in order to show nearby street foods")

    }
}