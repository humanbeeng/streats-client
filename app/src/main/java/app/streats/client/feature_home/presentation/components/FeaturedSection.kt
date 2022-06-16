package app.streats.client.feature_home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.streats.client.core.presentation.ui.theme.CardBackground
import app.streats.client.core.presentation.ui.theme.SectionHeading
import app.streats.client.core.presentation.ui.theme.Shapes
import app.streats.client.core.presentation.ui.theme.Typography
import app.streats.client.feature_home.data.dto.ShopDTO
import coil.compose.AsyncImage


@Composable
fun FeaturedSection(featuredShops: List<ShopDTO> = emptyList(), onShopItemSelected: (ShopDTO) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Featuring .", style = SectionHeading, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )

        LazyRow(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(start = 16.dp)) {
            items(featuredShops) {
                FeaturedCard(it, onShopItemSelected)

            }

        }

    }
}

@Composable
fun FeaturedCard(shopDTO: ShopDTO, onShopItemSelected: (ShopDTO) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxSize()) {
            Card(
                modifier = Modifier
                    .height(125.dp)
                    .width(175.dp)
                    .padding(10.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() } // This is mandatory
                    ) {
                        onShopItemSelected(shopDTO)
                    },
                shape = Shapes.medium,
                elevation = 7.dp,
                backgroundColor = CardBackground

            ) {
                AsyncImage(
                    model = "https://picsum.photos/134",
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Row(
            Modifier
                .fillMaxSize()
                .padding(start = 12.dp)
        ) {
            Text(text = shopDTO.shopName, style = Typography.h5)
        }


    }
}