package com.example.jmtask.repository

import com.example.jmtask.model.MoviesDTO
import com.example.jmtask.util.ApiState
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getData(): Flow<ApiState<MoviesDTO>>
}