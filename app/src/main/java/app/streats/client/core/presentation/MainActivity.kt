package app.streats.client.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import app.streats.client.core.presentation.components.Navigation
import app.streats.client.core.presentation.ui.theme.StreatsTheme
import dagger.hilt.android.AndroidEntryPoint


/**
 * TODO : Resolve navigation issue of BottomNavBar
 *
 * TODO : Remove AppBar from applicationA
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            StreatsTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val interactionSource = remember { MutableInteractionSource() }

                val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

                Navigation(navController = navController)


            }


        }
    }
}



