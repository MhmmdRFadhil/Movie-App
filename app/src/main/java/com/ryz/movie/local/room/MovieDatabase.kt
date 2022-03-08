package com.ryz.movie.local.room

import androidx.room.Database
import com.ryz.movie.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase {
    abstract fun movieDao(): MovieDao
}