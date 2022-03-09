package com.ryz.movie.ui.movie.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ryz.movie.core.data.source.MovieRepository
import com.ryz.movie.core.data.source.local.entity.MovieEntity

class FavoriteViewModel(private val movieRepository: MovieRepository): ViewModel() {
    fun getFavMovie(): LiveData<List<MovieEntity>> = movieRepository.getFavoriteMovies()
}