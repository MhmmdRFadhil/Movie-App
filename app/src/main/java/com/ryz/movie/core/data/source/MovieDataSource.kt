package com.ryz.movie.core.data.source

import androidx.lifecycle.LiveData
import com.ryz.movie.core.data.source.local.entity.MovieEntity
import com.ryz.movie.core.data.source.local.entity.UpComingMovieEntity
import com.ryz.movie.core.vo.Resource

interface MovieDataSource {

    fun getNowPlayingMovies(): LiveData<Resource<List<MovieEntity>>>

    fun getDetailNowPlayingMovies(moviesId: Int): LiveData<Resource<MovieEntity>>

    fun getUpcomingMovies(): LiveData<Resource<List<UpComingMovieEntity>>>

    fun getFavoriteMovies(): LiveData<List<MovieEntity>>

    fun setFavoriteMovies(movieEntity: MovieEntity, state: Boolean)
}