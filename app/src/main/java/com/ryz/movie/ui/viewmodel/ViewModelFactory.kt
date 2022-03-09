package com.ryz.movie.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ryz.movie.core.data.source.MovieRepository
import com.ryz.movie.core.di.Injection
import com.ryz.movie.ui.movie.detail.DetailViewModel
import com.ryz.movie.ui.movie.favorite.FavoriteViewModel
import com.ryz.movie.ui.movie.nowplaying.MovieViewModel
import com.ryz.movie.ui.movie.upcoming.UpcomingMovieViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(private val movieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(UpcomingMovieViewModel::class.java) -> {
                UpcomingMovieViewModel(movieRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class : ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideMovieRepository(context))
            }
    }
}