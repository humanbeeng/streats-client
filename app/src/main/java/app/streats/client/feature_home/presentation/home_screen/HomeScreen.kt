package app.streats.client.feature_home.presentation.home_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.streats.client.feature_home.data.dto.ShopDTO

/**
 * TODO : Add Shimmer Cards while loading
 */

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    onShopItemSelected: (ShopDTO) -> Unit,
    onLoggedOut: () -> Unit
) {

    val homeScreenState = homeScreenViewModel.homeState.value

    LaunchedEffect(key1 = true) {
        homeScreenViewModel.outgoingHomeScreenEventFlow.collect { homeEvent ->
            when (homeEvent) {
                is HomeEvent.Logout -> onLoggedOut()
            }
        }

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("HomeScreen")
        Button(onClick = { homeScreenViewModel.homeEventListener(HomeEvent.Logout) }) {
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
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(6.dp)
                            .clickable {
                                onShopItemSelected(it)
                            }
                    ) {
                        Text(text = it.shopName)

                    }
                }
            }
        }

    }
}