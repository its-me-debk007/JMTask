package com.example.jmtask.repository

import com.example.jmtask.model.MoviesDTO
import com.example.jmtask.network.ApiService
import com.example.jmtask.util.ApiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService) : Repository {

    override fun getData(): Flow<ApiState<MoviesDTO>> = flow {
        emit(ApiState.Loading())

        emit(ApiState.Success(data = apiService.getData()))

    }.catch { e ->
        emit(ApiState.Error(msg = e.message.toString()))
    }
}