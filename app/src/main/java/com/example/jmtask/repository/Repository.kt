package com.example.jmtask.repository

import com.example.jmtask.model.Result
import com.example.jmtask.util.ApiState
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getMovies(): Flow<ApiState<List<Result>>>
    fun searchMovies(query: String): Flow<ApiState<List<Result>>>
}