package app.streats.client.feature_order.presentation.order_history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.streats.client.feature_order.domain.models.OrderSummary

@Composable
fun OrderHistoryScreen(orderHistoryViewModel: OrderHistoryViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Order Screen")

        val orderHistoryState = orderHistoryViewModel.orderHistoryState.value

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            orderHistoryState.data?.let { it ->
                items(it) {
                    OrderSummaryCard(orderSummary = it)
                }
            }
        }
    }
}


@Composable
fun OrderSummaryCard(orderSummary: OrderSummary) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            orderSummary.orderItems.forEach {
                Text(text = it.itemName)
                Spacer(modifier = Modifier.height(10.dp))
            }

        }
    }
}