package app.streats.client.feature_home.presentation.shop_screen

import app.streats.client.feature_home.data.dto.ShopDTO

data class ShopScreenState(
    val data: ShopDTO? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)