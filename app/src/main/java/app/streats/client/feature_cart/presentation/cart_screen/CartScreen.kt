package app.streats.client.feature_cart.presentation.cart_screen

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.streats.client.R
import app.streats.client.core.presentation.components.Loader
import app.streats.client.core.presentation.ui.theme.*
import app.streats.client.core.util.CoreConstants.Rupees
import app.streats.client.feature_cart.domain.models.Cart
import app.streats.client.feature_cart.domain.models.CartItem
import app.streats.client.feature_cart.presentation.cart_screen.components.CartButtons
import app.streats.client.feature_cart.presentation.cart_screen.components.CheckoutSection
import app.streats.client.feature_cart.presentation.checkout.CheckoutState
import app.streats.client.feature_cart.presentation.contracts.CheckoutContract
import app.streats.client.feature_home.presentation.shop_screen.StreatsDivider
import com.airbnb.lottie.compose.*


@Composable
fun CartScreen(
    cartScreenViewModel: CartScreenViewModel = hiltViewModel(),
    onPaymentSuccess: () -> Unit,
    onPaymentFailure: () -> Unit,
    onGoBack: () -> Unit
) {

    val cartScreenState = cartScreenViewModel.cartState.value

    val checkoutActivity = rememberLauncherForActivityResult(
        contract = CheckoutContract()
    ) {
        if (it == RESULT_OK) {
            onPaymentSuccess()
        } else onPaymentFailure()
    }

    val isCheckoutInitiated = remember { mutableStateOf(false) }

    if (isCheckoutInitiated.value) Loader()

//    TODO : Add UI feedback
    LaunchedEffect(key1 = true) {
        cartScreenViewModel.checkoutState.collect {
            when (it) {
                is CheckoutState.Loading -> {
                    isCheckoutInitiated.value = true
                }
                is CheckoutState.Success -> {
                    checkoutActivity.launch(it.data)
                }

                is CheckoutState.Error -> {

                }
            }
        }

    }

    if (cartScreenState.data != null) {
        val cart = cartScreenState.data
//    UI

        if (cart.itemCount == 0) {
            EmptyCartScreen(onGoBack = { onGoBack() })
        } else {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(), backgroundColor = LightGrayBackground,
                topBar = { CartScreenTopBar(shopName = "Cart") },
                bottomBar = {
                    CheckoutSection(
                        onCheckout = { cartScreenViewModel.cartEventHandler(CartEvent.Checkout) },
                        cart.totalCost
                    )
                }

            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    CartItemsSection(
                        onItemAdded = { dishId, shopId ->
                            cartScreenViewModel.cartEventHandler(
                                CartEvent.AddToCart(
                                    dishId,
                                    shopId
                                )
                            )
                        },
                        onItemRemoved = { dishId, shopId ->
                            cartScreenViewModel.cartEventHandler(
                                CartEvent.RemoveFromCart(
                                    dishId,
                                    shopId
                                )
                            )
                        }, cart = cart
                    )
//              TODO : Add commission here
                    BillDetailsSection(itemsCost = cart.totalCost, 0.00, toPay = cart.totalCost)
                }
            }
        }

    } else {
        Loader(backgroundColor = LightGrayBackground.copy(0.9f))
    }


}


@Composable
fun EmptyCartScreen(onGoBack: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val isPlaying by remember {
                mutableStateOf(true)
            }

            val speed by remember {
                mutableStateOf(1f)
            }


            // remember lottie composition ,which
            // accepts the lottie composition result
            val composition by rememberLottieComposition(
                LottieCompositionSpec
                    .RawRes(R.raw.empty_cart)
            )
            // to control the animation
            val progress by animateLottieCompositionAsState(
                // pass the composition created above
                composition,

                // Iterates Forever
                iterations = LottieConstants.IterateForever,

                // pass isPlaying we created above,
                // changing isPlaying will recompose
                // Lottie and pause/play
                isPlaying = isPlaying,

                speed = speed,

                // this makes animation to restart when paused and play
                // pass false to continue the animation at which is was paused
                restartOnPlay = false

            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                LottieAnimation(
                    composition,
                    progress,
                    modifier = Modifier
                        .size(200.dp)
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "Cart is empty .", style = SectionHeading)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onGoBack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(10.dp)
                ) {
                    Text(text = "Go Back")
                }
            }


        }
    }
}


@Composable
fun CartItemCard(
    cartItem: CartItem,
    onItemAdded: (dishId: String, shopId: String) -> Unit,
    onItemRemoved: (dishId: String, shopId: String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        color = CardBackground
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(Modifier.weight(6f)) {
                Row {
                    Text(text = cartItem.itemName, style = DishName)
                }
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Start) {
                    Column(
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, end = 4.dp)
                    ) {
                        Text(text = "$Rupees${cartItem.price * cartItem.quantity}", style = Price)
                    }
                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
                        Text(text = "Quantity ${cartItem.quantity}", style = Price)
                    }

                }
            }

            Column(Modifier.weight(2f)) {
                CartButtons(
                    onItemRemoved = { onItemRemoved(cartItem.dishId, cartItem.shopId) },
                    onItemAdded = { onItemAdded(cartItem.dishId, cartItem.shopId) }
                )
            }

        }
    }

}

@Composable
fun CartScreenTopBar(shopName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        backgroundColor = CardBackground,
        elevation = 7.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp, top = 50.dp),
        ) {

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp, top = 50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = shopName, style = CartScreenShopName)
        }

    }
}

@Composable
fun BillDetailsSection(itemsCost: Double, taxesAndCharges: Double, toPay: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            Text(text = "Bill Details .", style = Caption)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(), shape = Shapes.medium, backgroundColor = CardBackground
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
//                    TODO : Remove string
                    BillDetailItems(itemName = "Items Cost", itemCost = itemsCost)
                    BillDetailItems(itemName = "Taxes and Charges", itemCost = taxesAndCharges)
                    Spacer(modifier = Modifier.height(2.dp))
                    StreatsDivider()
                    Spacer(modifier = Modifier.height(2.dp))

                    ToPay(toPay = toPay)
                }
            }
        }
    }

}

@Composable
fun CartItemsSection(
    cart: Cart,
    onItemAdded: (dishId: String, shopId: String) -> Unit,
    onItemRemoved: (dishId: String, shopId: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp)
        ) {
            Text(text = "Items .", style = Caption)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(), shape = Shapes.medium, backgroundColor = CardBackground
            ) {
                LazyColumn(contentPadding = PaddingValues(6.dp)) {

//                TODO : Add ShopNameText Composable here

//                    item { StreatsDivider() }
                    items(cart.cartItems) {
                        CartItemCard(
//                            TODO : Replace this string
                            cartItem = it,
                            onItemAdded = { dishId, shopId ->
                                onItemAdded(dishId, shopId)
                            },
                            onItemRemoved = { dishId, shopId ->
                                onItemRemoved(dishId, shopId)
                            }
                        )
                    }

                }
            }
        }

    }

}

@Composable
fun ShopNameText(shopName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Text(text = "Ramu Chats", style = NearbyCardTitle)
    }
}

@Composable
fun BillDetailItems(itemName: String, itemCost: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(text = itemName, style = BillDetails)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), horizontalAlignment = Alignment.End
        ) {
            Text(text = "$Rupees$itemCost", style = BillDetails)
        }

    }
}


@Composable
fun ToPay(toPay: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(text = "To Pay", style = BillDetails)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), horizontalAlignment = Alignment.End
        ) {
            Text(text = "${Rupees}${toPay}", style = BillDetails)
        }

    }
}

