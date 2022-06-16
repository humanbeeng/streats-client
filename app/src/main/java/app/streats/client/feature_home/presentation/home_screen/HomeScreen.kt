package app.streats.client.feature_home.presentation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.streats.client.R
import app.streats.client.core.presentation.components.Loader
import app.streats.client.core.presentation.ui.theme.LightGrayBackground
import app.streats.client.core.presentation.ui.theme.SectionHeading
import app.streats.client.feature_cart.presentation.components.CartFloatingButton
import app.streats.client.feature_home.data.dto.ShopDTO
import app.streats.client.feature_home.presentation.components.FeaturedSection
import app.streats.client.feature_home.presentation.components.GreetingSection
import app.streats.client.feature_home.presentation.components.NearbyShopCard
import app.streats.client.feature_home.presentation.components.TopBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

/**
 * TODO : Add Shimmer Cards while loading
 */

@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    onShopItemSelected: (ShopDTO) -> Unit,
    onLoggedOut: () -> Unit,
    onCartClicked: () -> Unit
) {

    val homeScreenState = homeScreenViewModel.homeState.value

    LaunchedEffect(key1 = true) {
        homeScreenViewModel.outgoingHomeScreenEventFlow.collect { homeOutgoingEvent ->
            when (homeOutgoingEvent) {
                is HomeOutgoingEvent.Logout -> onLoggedOut()
            }
        }

    }

    HomeScreenUI(
        homeScreenState = homeScreenState,
        homeScreenViewModel = homeScreenViewModel,
        onCartClicked = onCartClicked,
        onShopItemSelected = onShopItemSelected,
        onLoggedOut = { homeScreenViewModel.homeEventListener(HomeEvent.Logout) }
    )

}


@Composable
fun HomeScreenUI(
    homeScreenState: HomeScreenState,
    homeScreenViewModel: HomeScreenViewModel,
    onCartClicked: () -> Unit,
    onShopItemSelected: (ShopDTO) -> Unit,
    onLoggedOut: () -> Unit
) {
    val isRefreshing = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isRefreshing.value) {
        if (isRefreshing.value) {

            delay(800)
            homeScreenViewModel.homeEventListener(HomeEvent.Refresh)
            isRefreshing.value = false
        }
    }



    if (homeScreenState.isLoading.not() && homeScreenState.data != null && isRefreshing.value.not()) {

        val featuredShops = homeScreenState.data.featuredShops

        val nearbyShops = homeScreenState.data.nearbyShops

        val username = homeScreenState.data.username

        val subLocality = homeScreenViewModel.currentLocation.subLocality

        val itemCount = homeScreenState.data.itemCount

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            backgroundColor = LightGrayBackground,
            floatingActionButton = {
                CartFloatingButton(onCartClicked = onCartClicked, itemCount = itemCount)
            }
        ) {


            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
                onRefresh = { isRefreshing.value = true },
                refreshTriggerDistance = 120.dp,
                clipIndicatorToPadding = false
            ) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {

                    item {
                        TopBar(onCartClicked, subLocality)
                    }

                    item { GreetingSection(username) }

                    item { FeaturedSection(nearbyShops, onShopItemSelected) }


                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Near You .", style = SectionHeading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                        )
                    }
                    items(nearbyShops) {
                        NearbyShopCard(shopDTO = it, onShopItemSelected)
                    }

                    item {
                        Button(onClick = { onLoggedOut() }) {
                            Text(text = "Logout")
                        }
                    }


                }

            }
        }
    } else {
        Loader()
    }

}


@Composable
fun MenuButton(onMenuClicked: () -> Unit = {}) {
    IconButton(onClick = { onMenuClicked() }) {
        Image(
            painter = painterResource(id = R.drawable.menu),
            contentDescription = "Menu",
            modifier = Modifier.size(32.dp)
        )
    }
}

