package app.streats.client.di

import app.streats.client.core.util.CoreConstants
import app.streats.client.feature_home.data.HomeApi
import app.streats.client.feature_home.data.ShopApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.BASIC)

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    @Singleton
    @Provides
    fun providesHomeApi(): HomeApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(CoreConstants.SERVER_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(HomeApi::class.java)
    }

    @Singleton
    @Provides
    fun providesShopApi(): ShopApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(CoreConstants.SERVER_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ShopApi::class.java)
    }
}