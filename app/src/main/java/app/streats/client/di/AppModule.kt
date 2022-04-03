package app.streats.client.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import app.streats.client.core.util.AccessToken
import app.streats.client.feature_auth.util.AuthConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("streats", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesAccessToken(app: Application): AccessToken {
        val sharedPreferences = providesSharedPreferences(app)
        val accessToken = sharedPreferences.getString(AuthConstants.ACCESS_TOKEN_PREF, "") ?: ""
        return AccessToken(accessToken)
    }

}