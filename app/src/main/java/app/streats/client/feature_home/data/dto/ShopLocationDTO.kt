package app.streats.client.feature_home.data.dto

data class ShopLocationDTO(
    val x : String,
    val y : String,
    val type : String,
    val coordinates : List<String>
)