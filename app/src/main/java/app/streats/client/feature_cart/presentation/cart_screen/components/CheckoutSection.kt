package app.streats.client.feature_cart.presentation.cart_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.streats.client.core.presentation.ui.theme.CardBackground
import app.streats.client.core.presentation.ui.theme.CheckoutCardText
import app.streats.client.core.presentation.ui.theme.CheckoutTotal
import app.streats.client.core.presentation.ui.theme.SuccessGreen
import app.streats.client.core.util.CoreConstants

@Composable
fun CheckoutSection(onCheckout: () -> Unit, totalCost: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(110.dp),
        elevation = 7.dp,
        backgroundColor = CardBackground,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 35.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TotalCostText(totalCost = totalCost)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                PayButton(onCheckout = onCheckout)
            }

        }
    }


}


@Composable
fun TotalCostText(totalCost: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${CoreConstants.Rupees}$totalCost", style = CheckoutTotal)
    }
}

@Composable
fun PayButton(onCheckout: () -> Unit) {
    Card(
        backgroundColor = SuccessGreen,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onCheckout()
            }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Proceed to pay", style = CheckoutCardText)
        }
    }
}
