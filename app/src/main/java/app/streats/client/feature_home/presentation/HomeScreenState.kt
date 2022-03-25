package app.streats.client.feature_home.presentation

import app.streats.client.feature_home.data.dto.HomeDTO

data class HomeScreenState(
    val data: HomeDTO? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
