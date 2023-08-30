package com.example.jmtask.repository

import com.example.jmtask.model.MoviesDTO
import com.example.jmtask.util.ApiState
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getMovies(): Flow<ApiState<MoviesDTO>>
    fun searchMovies(query: String): Flow<ApiState<MoviesDTO>>
}