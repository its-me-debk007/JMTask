package com.example.jmtask.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jmtask.model.Result

@Dao
interface FavouritesMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: Result)

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<Result>
}