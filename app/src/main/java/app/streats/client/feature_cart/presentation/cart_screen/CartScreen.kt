package app.streats.client.feature_cart.presentation.cart_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun CartScreen(cartScreenViewModel: CartScreenViewModel = hiltViewModel()) {

    val cartScreenState = cartScreenViewModel.cartState.value

    Column(modifier = Modifier.fillMaxSize()) {


        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            cartScreenState.cartData?.cartItems?.let { it ->
                items(it) {
                    Text(text = it.itemName)

                }
            }
        }

    }

}
