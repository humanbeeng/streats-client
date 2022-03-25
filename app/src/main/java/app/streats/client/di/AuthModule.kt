package app.streats.client.di

import app.streats.client.core.util.Constants
import app.streats.client.feature_auth.data.AuthApi
import com.google.firebase.auth.FirebaseAuth
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
object AuthModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
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