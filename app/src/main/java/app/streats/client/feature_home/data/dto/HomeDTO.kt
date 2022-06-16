package app.streats.client.feature_home.data.dto

data class HomeDTO(
    val username: String,
    val featuredShops: List<ShopDTO>,
    val nearbyShops: List<ShopDTO>,
    val itemCount: Int = 0
)