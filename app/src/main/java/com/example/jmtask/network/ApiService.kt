package com.example.jmtask.network

import com.example.jmtask.model.MoviesDTO
import retrofit2.http.GET

interface ApiService {

    @GET("discover/movie")
    suspend fun getData(): MoviesDTO
}