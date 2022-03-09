package com.ryz.movie.ui.movie.nowplaying

import androidx.lifecycle.ViewModel
import com.ryz.movie.core.data.source.MovieRepository

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getNowPlayingMovies() = movieRepository.getNowPlayingMovies()
}