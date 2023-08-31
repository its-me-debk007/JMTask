package com.example.jmtask.di

import android.content.Context
import androidx.room.Room
import com.example.jmtask.BuildConfig
import com.example.jmtask.network.ApiService
import com.example.jmtask.repository.Repository
import com.example.jmtask.repository.RepositoryImpl
import com.example.jmtask.room.MovieDatabase
import com.example.jmtask.room.RemoteMovieDao
import com.example.jmtask.util.MOVIE_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesInterceptor(): Interceptor = Interceptor { chain ->
        val originalReq = chain.request()
        val modifiedReq = originalReq.newBuilder()
            .url(
                originalReq.url().newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()
            )
            .build()

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
    fun providesRepository(apiService: ApiService, remoteMovieDao: RemoteMovieDao): Repository =
        RepositoryImpl(apiService, remoteMovieDao)

    @Provides
    @Singleton
    fun providesMovieDatabase(@ApplicationContext applicationContext: Context) =
        Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java,
            MOVIE_DB
        ).build()

    @Provides
    fun providesRemoteMovieDao(movieDatabase: MovieDatabase): RemoteMovieDao =
        movieDatabase.remoteMovieDao()

}