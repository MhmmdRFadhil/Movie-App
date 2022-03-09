package com.ryz.movie.ui.movie.upcoming

import androidx.lifecycle.ViewModel
import com.ryz.movie.core.data.source.MovieRepository

class UpcomingMovieViewModel(private val movieRepository: MovieRepository): ViewModel() {
    fun getUpcomingMovies() = movieRepository.getUpcomingMovies()
}