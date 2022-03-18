package app.streats.client.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import app.streats.client.core.util.Constants
import app.streats.client.feature_auth.data.AuthApi
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
object AppModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("streats", MODE_PRIVATE)
    }


    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.BASIC)

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    @Singleton
    @Provides
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.SERVER_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

}