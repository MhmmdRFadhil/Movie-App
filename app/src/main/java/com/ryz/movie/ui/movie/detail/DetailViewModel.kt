package com.ryz.movie.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ryz.movie.core.data.source.MovieRepository
import com.ryz.movie.core.data.source.local.entity.MovieEntity
import com.ryz.movie.core.vo.Resource

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private lateinit var detailNowPlayingMovies: LiveData<Resource<MovieEntity>>

    fun setMovie(id: Int, category: String) {
        when (category) {
            TYPE_NOW_PLAYING -> {
                detailNowPlayingMovies = movieRepository.getDetailNowPlayingMovies(id)
            }
        }
    }

    fun setFavorite(movieEntity: MovieEntity) {
        val newState = !movieEntity.isFav
        movieRepository.setFavoriteMovies(movieEntity, newState)
    }

    fun getDetailNowPlayingMovies() = detailNowPlayingMovies

    companion object {
        const val TYPE_NOW_PLAYING = "now_playing"
    }
}