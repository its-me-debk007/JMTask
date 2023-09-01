package com.example.jmtask.repository

import android.util.Log
import com.example.jmtask.model.Result
import com.example.jmtask.network.ApiService
import com.example.jmtask.room.RemoteMovieDao
import com.example.jmtask.util.ApiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val remoteMovieDao: RemoteMovieDao
) : Repository {

    override fun getMovies(): Flow<ApiState<List<Result>>> = flow {
        emit(ApiState.Loading())

        val moviesDTO = apiService.getMovies()

        Log.d("RETRO", moviesDTO.toString())

        remoteMovieDao.insertMovies(moviesDTO.results)

        emit(ApiState.Success(data = moviesDTO.results))

    }.catch { e ->

        Log.d("RETRO", e.message.toString())

        if (e.message?.length!! > 7 && e.message?.substring(0, 22) == "Unable to resolve host") {
            emit(ApiState.Success(data = remoteMovieDao.getMovies()))
        } else
            emit(ApiState.Error(msg = e.message.toString()))
    }

    override fun searchMovies(query: String): Flow<ApiState<List<Result>>> = flow {
        emit(ApiState.Loading())

        emit(ApiState.Success(data = apiService.searchMovies(query).results))

    }.catch { e ->
        val msg = if (e.message?.length!! > 7 && e.message?.substring(0, 22) == "Unable to resolve host")
            "No Internet Connection!" else e.message.toString()

        emit(ApiState.Error(msg))
    }
}