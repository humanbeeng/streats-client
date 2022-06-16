package app.streats.client.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import app.streats.client.core.domain.models.AccessToken
import app.streats.client.core.domain.models.FCMToken
import app.streats.client.feature_auth.domain.models.CurrentLocation
import app.streats.client.feature_auth.util.AuthConstants
import com.google.firebase.messaging.FirebaseMessaging
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

    @Provides
    @Singleton
    fun providesFirebaseMessagingInstance(): FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }

    @Provides
    @Singleton
    fun providesCurrentLocationCoordinates(): CurrentLocation {
        return CurrentLocation(0.00, 0.00)
    }

    @Provides
    @Singleton
    fun providesFcmToken(): FCMToken {
        return FCMToken()
    }

}