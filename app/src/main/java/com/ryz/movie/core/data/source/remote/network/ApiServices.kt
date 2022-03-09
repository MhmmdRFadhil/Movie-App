package com.ryz.movie.core.data.source.remote.network

import com.ryz.movie.core.data.source.remote.response.ListResponse
import com.ryz.movie.core.data.source.remote.response.MovieDetailResponse
import com.ryz.movie.core.data.source.remote.response.MovieResponse
import com.ryz.movie.core.utils.NetworkInfo.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<ListResponse<MovieResponse>>

    @GET("movie/{movie_id}")
    fun getDetailNowPlayingMovies(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Call<MovieDetailResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY,
    ): Call<ListResponse<MovieResponse>>

}