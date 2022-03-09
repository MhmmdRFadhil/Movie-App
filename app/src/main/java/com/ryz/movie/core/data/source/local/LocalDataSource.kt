package com.ryz.movie.core.data.source.local

import androidx.lifecycle.LiveData
import com.ryz.movie.core.data.source.local.entity.MovieEntity
import com.ryz.movie.core.data.source.local.entity.UpComingMovieEntity
import com.ryz.movie.core.data.source.local.room.MovieDao

class LocalDataSource(private val movieDao: MovieDao) {

    fun getNowPlayingMovies(): LiveData<List<MovieEntity>> = movieDao.getNowPlayingMovies()

    fun getDetailNowPlayingMovies(id: Int): LiveData<MovieEntity> =
        movieDao.getDetailNowPlayingMovie(id)

    fun getUpcomingMovies(): LiveData<List<UpComingMovieEntity>> = movieDao.getUpcomingMovies()

    fun getFavoriteMovie(): LiveData<List<MovieEntity>> = movieDao.getFavoriteMovie()

    fun insertMovie(movie: List<MovieEntity>) = movieDao.insertMovie(movie)

    fun insertUpcomingMovie(movie: List<UpComingMovieEntity>) = movieDao.insertUpcomingMovie(movie)

    fun setFavoriteMovie(movieEntity: MovieEntity, newState: Boolean) {
        movieEntity.isFav = newState
        movieDao.updateMovie(movieEntity)
    }

    fun updateMovies(movieEntity: MovieEntity, newState: Boolean) {
        movieEntity.isFav = newState
        movieDao.updateMovie(movieEntity)
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }
}