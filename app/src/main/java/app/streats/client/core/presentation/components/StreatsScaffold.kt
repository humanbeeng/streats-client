package app.streats.client.core.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StreatsScaffold(content: @Composable () -> Unit) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        content()
    }
}