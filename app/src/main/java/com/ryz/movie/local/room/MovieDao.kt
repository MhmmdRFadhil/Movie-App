package com.ryz.movie.local.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ryz.movie.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE id=:id")
    fun getMovieById(id: Int): Flow<MovieEntity>

    @Query("SELECT * FROM movie WHERE isFav = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: List<MovieEntity>)

    @Update
    fun updateFavoriteMovie(movieEntity: MovieEntity)
}