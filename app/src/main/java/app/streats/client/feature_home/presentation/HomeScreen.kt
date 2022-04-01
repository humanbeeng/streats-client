package app.streats.client.feature_home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * TODO : Add Shimmer Cards while loading
 */

@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel = hiltViewModel()) {


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val homeScreenState = homeScreenViewModel.homeState.value
        Text("HomeScreen")
        Button(onClick = { homeScreenViewModel.logout() }) {
            Text(text = "Logout")

        }
        val username = homeScreenState.data?.username
        Text(text = username.toString())

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            homeScreenState.data?.nearbyShops?.let { it ->
                items(it) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(6.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp
                    ) {
                        Text(text = it.shopName)

                    }
                }
            }
        }

    }
}