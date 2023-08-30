package com.example.jmtask.network

import com.example.jmtask.model.MoviesDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getMovies(): MoviesDTO

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String
    ): MoviesDTO
}