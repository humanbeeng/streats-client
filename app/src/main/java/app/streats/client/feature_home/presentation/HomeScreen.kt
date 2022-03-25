package app.streats.client.feature_home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.fillMaxSize()) {

        val homeScreenState = homeScreenViewModel.homeState.value

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("HomeScreen")
            Button(onClick = { homeScreenViewModel.logout() }) {
                Text(text = "Logout")

            }
            val username = homeScreenState.data?.username
            Text(text = username.toString())

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                homeScreenState.data?.nearbyShops?.let { it ->
                    items(it) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(text = it.shopName)

                        }
                    }
                }
            }
        }

    }
}