package app.streats.client.feature_home.data.dto


data class ShopDTO(
    val shopId: String,
    val shopName: String,
    val shopOwnerPhoneNumber: String,
    val location: ShopLocationDTO,
    val zipcode: String,
    val shopItems: MutableMap<String, DishItemDTO>,
    val isTakeawaySupported: Boolean,
    val isShopOpen: Boolean,
    val featured: Boolean
)