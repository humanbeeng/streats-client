package app.streats.client.feature_home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.streats.client.core.presentation.ui.theme.NearbyCardTitle
import app.streats.client.core.presentation.ui.theme.Shapes
import app.streats.client.feature_home.data.dto.ShopDTO
import coil.compose.AsyncImage


@Composable
fun NearbyShopCard(shopDTO: ShopDTO, onShopItemSelected: (ShopDTO) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(14.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() } // This is mandatory
            ) {
                onShopItemSelected(shopDTO)
            },
        shape = Shapes.medium,
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(8f)
            ) {
//                TODO : Change this to shopDto.image
                AsyncImage(
                    model = "https://picsum.photos/134",
                    contentDescription = "NearbyShopCard",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, top = 10.dp)
                        .weight(6f),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(text = shopDTO.shopName, style = NearbyCardTitle)
                    ShopLocation(shopLocation = "Gopala Extension", modifier = Modifier.padding(top = 2.dp, end = 2.dp))

                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 16.dp)
                        .weight(2f),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    ShopStatus(isShopOpen = shopDTO.isShopOpen, modifier = Modifier.padding(end = 2.dp))
                }
            }
        }
    }
}

