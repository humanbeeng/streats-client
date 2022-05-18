package app.streats.client.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import app.streats.client.core.presentation.components.Navigation
import app.streats.client.core.presentation.ui.theme.StreatsTheme
import dagger.hilt.android.AndroidEntryPoint


/**
 * TODO : Resolve navigation issue of BottomNavBar
 *
 * TODO : Remove AppBar from application
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )

        setContent {
            StreatsTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val interactionSource = remember { MutableInteractionSource() }
                Navigation(
                    navController = navController
                )
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation(navController = navController)
                }

            }
        }
    }
}


