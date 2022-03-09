package com.ryz.movie.core.di

import android.content.Context
import com.ryz.movie.core.data.source.MovieRepository
import com.ryz.movie.core.data.source.local.LocalDataSource
import com.ryz.movie.core.data.source.local.room.MovieDatabase
import com.ryz.movie.core.data.source.remote.RemoteDataSource
import com.ryz.movie.core.utils.AppExecutors

object Injection {
    fun provideMovieRepository(context: Context): MovieRepository {
        val database = MovieDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()
        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

}