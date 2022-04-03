package app.streats.client.feature_cart.presentation.cart_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.streats.client.feature_cart.domain.models.CartItem


@Composable
fun CartScreen(cartScreenViewModel: CartScreenViewModel = hiltViewModel()) {

    val cartScreenState = cartScreenViewModel.cartState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {


        Row {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                cartScreenState.cartData?.cartItems?.let { it ->
                    items(it) {
                        CartItemCard(cartItem = it, cartScreenViewModel = cartScreenViewModel)
                    }
                }
            }
        }
        Row(modifier = Modifier.padding(bottom = 60.dp)) {
            if (cartScreenState.cartData?.itemCount?.let { it > 0 } == true) {
                CheckoutCard(cartScreenViewModel)
            }
        }


    }


}

@Composable
fun CartItemCard(cartItem: CartItem, cartScreenViewModel: CartScreenViewModel) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Column {
                Text(text = cartItem.itemName)
                Text(text = cartItem.price.toString())
                Text(text = cartItem.quantity.toString())

            }
            Column {
                Button(onClick = {
                    cartScreenViewModel.cartEventHandler(
                        CartEvent.AddToCart(
                            cartItem.dishId,
                            cartItem.shopId
                        )
                    )
                }) {
                    Text(text = "Add to cart")
                }

                Button(onClick = {
                    cartScreenViewModel.cartEventHandler(
                        CartEvent.RemoveFromCart(
                            cartItem.dishId,
                            cartItem.shopId
                        )
                    )
                }) {
                    Text(text = "Remove from cart")
                }

            }


        }
    }

}

@Composable
fun CheckoutCard(cartScreenViewModel: CartScreenViewModel) {
    Card(
        backgroundColor = Color(0xFF00B649),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                cartScreenViewModel.cartEventHandler(
                    CartEvent.RemoveFromCart(
                        "6237fc647f0efa7964957434",
                        "6237fc647f0efa796495743b"
                    )
                )
            }) {
        Text("Proceed to pay")
    }


}



