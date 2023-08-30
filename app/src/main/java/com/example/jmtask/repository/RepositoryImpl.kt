package com.example.jmtask.repository

import com.example.jmtask.model.Result
import com.example.jmtask.network.ApiService
import com.example.jmtask.room.MovieDao
import com.example.jmtask.util.ApiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) : Repository {

    override fun getMovies(): Flow<ApiState<List<Result>>> = flow {
        emit(ApiState.Loading())

        val moviesDTO = apiService.getMovies()

        movieDao.insertMovies(moviesDTO.results)

        emit(ApiState.Success(data = moviesDTO.results))

    }.catch { e ->

        if (e.message?.length!! > 7 && e.message?.substring(0, 22) == "Unable to resolve host") {
            emit(ApiState.Success(data = movieDao.getMovies()))
        } else
            emit(ApiState.Error(msg = e.message.toString()))
    }

    override fun searchMovies(query: String): Flow<ApiState<List<Result>>> = flow {
        emit(ApiState.Loading())

        emit(ApiState.Success(data = apiService.searchMovies(query).results))

    }.catch { e ->
        emit(ApiState.Error(msg = e.message.toString()))
    }
}