package app.streats.client.di

import app.streats.client.core.util.Constants
import app.streats.client.feature_cart.data.CartApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object CartModule {

    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.BASIC)

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()


    @Provides
    fun providesCartApi(): CartApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.SERVER_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(CartApi::class.java)
    }
}