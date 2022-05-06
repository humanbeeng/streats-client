package app.streats.client.core.domain.models

import app.streats.client.core.util.CoreConstants.EMPTY

data class AccessToken(
    var value: String = EMPTY
) {
    fun setAccessToken(accessToken: String): String {
        value = "Bearer $accessToken"
        return value
    }
}