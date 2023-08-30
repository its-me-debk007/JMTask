package com.example.jmtask.di

import android.util.Log
import com.example.jmtask.BuildConfig
import com.example.jmtask.network.ApiService
import com.example.jmtask.repository.Repository
import com.example.jmtask.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesInterceptor() : Interceptor= Interceptor {chain ->
        val originalReq = chain.request()
        val modifiedReq = originalReq.newBuilder()
            .url(originalReq.url().newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
            )
            .build()

        Log.d("RETRO", modifiedReq.toString())

        chain.proceed(modifiedReq)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesRepository(apiService: ApiService): Repository = RepositoryImpl(apiService)
}