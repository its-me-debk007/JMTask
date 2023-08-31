package com.example.jmtask.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jmtask.model.Result

@Database(entities = [Result::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun remoteMovieDao(): RemoteMovieDao

    abstract fun favouritesMovieDao(): FavouritesMovieDao
}