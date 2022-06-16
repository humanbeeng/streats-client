package app.streats.client.feature_home.presentation.shop_screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.streats.client.core.presentation.components.Loader
import app.streats.client.core.presentation.ui.theme.*
import app.streats.client.feature_cart.presentation.components.CartFloatingButton
import app.streats.client.feature_home.data.dto.ShopDTO
import app.streats.client.feature_home.domain.models.DishItem
import app.streats.client.feature_home.presentation.components.ShopLocation
import app.streats.client.feature_home.presentation.components.ShopStatus
import app.streats.client.feature_home.presentation.shop_screen.components.AddRemoveButtons
import coil.compose.AsyncImage
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ShopScreen(
    shopScreenViewModel: ShopScreenViewModel = hiltViewModel(),
    onCartClicked: () -> Unit
) {

    val context = LocalContext.current
    val shopScreenState = shopScreenViewModel.shopState.value

    val shop = shopScreenState.data

    val scaffoldState = rememberScaffoldState()

    val dishItems = mutableListOf<DishItem>()
    shop?.shopItems?.map { item ->
        dishItems.add(
            DishItem(
                dishId = item.key,
                shopId = shop.shopId,
                dishName = item.value.dishName,
                price = item.value.price
            )
        )
    }

    LaunchedEffect(key1 = Unit) {

        shopScreenViewModel.isItemAdded.collectLatest {
            when (it) {
                true -> Toasty.success(context, "Item added", Toast.LENGTH_SHORT, true).show();
                false -> Toasty.warning(context, "You can only add items from same shop", Toast.LENGTH_LONG, true)
                    .show()
            }
        }

        shopScreenViewModel.isItemRemoved.collectLatest {
            when (it) {
                true -> Toasty.normal(context, "Item removed", Toast.LENGTH_SHORT).show()
                false -> Toasty.warning(context, "Item doesn't exist in cart", Toast.LENGTH_SHORT).show()
            }
        }


    }


    if (shop != null) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            backgroundColor = LightGrayBackground,
            floatingActionButton = {
                CartFloatingButton(onCartClicked = onCartClicked, itemCount = 0)
            },
            scaffoldState = scaffoldState
        ) {


            LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                TODO: Add actual shop image URL

                item {
                    ShopImage("https://picsum.photos/134")
                }

                item {
                    ShopDetailsCard(shop)
                }

                item { StreatsDivider() }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "Menu .", style = Caption)

                    }
                }

                items(dishItems) {
                    DishItemCard(
                        dishItem = it,
                        onItemAdded = {
                            shopScreenViewModel.shopEventHandler(
                                ShopEvent.AddToCart(
                                    it.dishId,
                                    it.shopId
                                )
                            )
                        }, onItemRemoved = {
                            shopScreenViewModel.shopEventHandler(
                                ShopEvent.RemoveFromCart(it.dishId, it.shopId)
                            )
                            Toasty.normal(context, "Item removed", Toast.LENGTH_SHORT).show();
                        }
                    )
                }
            }

        }

    } else Loader()


}


@Composable
fun ShopImage(shopImageUrl: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(300.dp), shape = Shapes.medium, elevation = 4.dp
    ) {
        AsyncImage(
            model = shopImageUrl,
            contentDescription = "Shop Image",
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )
    }

}


@Composable
fun ShopDetailsCard(shop: ShopDTO) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = shop.shopName, style = ShopName)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            StreatsDivider()
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                ShopLocation(shopLocation = "Gopala Extension", modifier = Modifier.padding(4.dp))
            }
            Column {
                ShopStatus(isShopOpen = shop.isShopOpen, modifier = Modifier.padding(4.dp))
            }
        }
    }
}


@Composable
fun DishItemCard(dishItem: DishItem, onItemAdded: () -> Unit, onItemRemoved: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(90.dp)
            .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
        elevation = 4.dp,
        shape = Shapes.medium,
        backgroundColor = CardBackground
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(Modifier.weight(6f)) {
                Text(text = dishItem.dishName, style = DishName)
                Text(text = "Rs. ${dishItem.price}", style = Price)
            }

            Column(Modifier.weight(2f)) {
                AddRemoveButtons(onItemAdded, onItemRemoved)
            }

        }

    }

}

@Composable
fun StreatsDivider(modifier: Modifier = Modifier) {
    Divider(modifier = modifier)
}