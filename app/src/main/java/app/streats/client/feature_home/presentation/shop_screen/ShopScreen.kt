package app.streats.client.feature_home.presentation.shop_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.streats.client.feature_cart.presentation.cart_screen.CartEvent
import app.streats.client.feature_cart.presentation.cart_screen.CartScreenViewModel
import app.streats.client.feature_home.domain.models.DishItem


@Composable
fun ShopScreen(
    shopScreenViewModel: ShopScreenViewModel = hiltViewModel(),
    cartScreenViewModel: CartScreenViewModel = hiltViewModel()
) {

    val shopScreenState = shopScreenViewModel.shopState.value

    val shopDetails = shopScreenState.data

    val shopItems = mutableListOf<DishItem>()
    shopDetails?.shopItems?.map { item ->
        shopItems.add(
            DishItem(
                dishId = item.key,
                shopId = shopDetails.shopId,
                dishName = item.value.dishName,
                price = item.value.price
            )
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(shopItems) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Row {

                    Column {

                        Text(it.dishName)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(it.price.toString())
                    }
                    Column {
                        Button(onClick = {
                            cartScreenViewModel.cartEventHandler(
                                CartEvent.AddToCart(
                                    it.dishId,
                                    it.shopId
                                )
                            )
                        }) {
                            Text(text = "Add to cart")
                        }

                        Button(onClick = {
                            shopScreenViewModel.shopEventHandler(
                                ShopEvent.RemoveFromCart(
                                    it.dishId,
                                    it.shopId
                                )
                            )
                        }) {
                            Text(text = "Remove from cart")
                        }

                    }
                }
            }
        }
    }


}