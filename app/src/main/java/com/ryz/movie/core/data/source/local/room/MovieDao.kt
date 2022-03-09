package com.ryz.movie.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ryz.movie.core.data.source.local.entity.MovieEntity
import com.ryz.movie.core.data.source.local.entity.UpComingMovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getNowPlayingMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE id=:id")
    fun getDetailNowPlayingMovie(id: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM upcoming")
    fun getUpcomingMovies(): LiveData<List<UpComingMovieEntity>>

    @Query("SELECT * FROM movie WHERE isFav = 1")
    fun getFavoriteMovie(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpcomingMovie(movie: List<UpComingMovieEntity>)

    @Update
    fun updateMovie(movieEntity: MovieEntity)
}