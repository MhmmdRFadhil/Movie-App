package com.ryz.movie.core.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ryz.movie.core.data.source.remote.network.ApiConfig
import com.ryz.movie.core.data.source.remote.network.ApiResponse
import com.ryz.movie.core.data.source.remote.response.MovieDetailResponse
import com.ryz.movie.core.data.source.remote.response.MovieResponse
import com.ryz.movie.core.utils.NetworkInfo.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

class RemoteDataSource {
    fun getNowPlayingMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        CoroutineScope(Dispatchers.IO).launch {
            ApiConfig.instance.getNowPlayingMovies(API_KEY).await().result?.let { listMovie ->
                withContext(Dispatchers.Main) {
                    resultMovies.value = ApiResponse.success(listMovie)
                }
            }
        }
        return resultMovies
    }

    fun getDetailNowPlayingMovies(moviesId: Int): LiveData<ApiResponse<MovieDetailResponse>> {
        val resultMovieDetail = MutableLiveData<ApiResponse<MovieDetailResponse>>()
        CoroutineScope(Dispatchers.IO).launch {
            ApiConfig.instance.getDetailNowPlayingMovies(moviesId, API_KEY).await()
                .let { listDetailMovies ->
                    withContext(Dispatchers.Main) {
                        resultMovieDetail.value = ApiResponse.success(listDetailMovies)
                    }
                }
        }
        return resultMovieDetail
    }

    fun getUpcomingMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        CoroutineScope(Dispatchers.IO).launch {
            ApiConfig.instance.getUpcomingMovies(API_KEY).await().result?.let { listUpcomingMovies ->
                withContext(Dispatchers.Main) {
                    resultMovies.value = ApiResponse.success(listUpcomingMovies)
                }
            }
        }
        return resultMovies
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }
}