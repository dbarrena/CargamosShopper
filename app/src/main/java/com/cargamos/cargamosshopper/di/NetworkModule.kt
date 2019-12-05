package com.cargamos.cargamosshopper.di

import androidx.annotation.NonNull
import com.cargamos.cargamosshopper.api.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(Api.CRTS_RIGHT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginService(): LoginService {
        return LoginService()
    }

    @Provides
    @Singleton
    fun providePickingService(): PickingService {
        return PickingService()
    }

    @Provides
    @Singleton
    fun provideShopperService(): ShopperService {
        return ShopperService()
    }

    @Provides
    @Singleton
    fun providePickingServiceInterface(@NonNull retrofit: Retrofit): PickingServiceInterface {
        return retrofit.create(PickingServiceInterface::class.java)
    }
}