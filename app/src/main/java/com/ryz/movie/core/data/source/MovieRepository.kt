package com.ryz.movie.core.data.source

import androidx.lifecycle.LiveData
import com.ryz.movie.core.data.source.local.LocalDataSource
import com.ryz.movie.core.data.source.local.entity.MovieEntity
import com.ryz.movie.core.data.source.local.entity.UpComingMovieEntity
import com.ryz.movie.core.data.source.remote.RemoteDataSource
import com.ryz.movie.core.data.source.remote.network.ApiResponse
import com.ryz.movie.core.data.source.remote.response.MovieDetailResponse
import com.ryz.movie.core.data.source.remote.response.MovieResponse
import com.ryz.movie.core.utils.AppExecutors
import com.ryz.movie.core.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieDataSource {
    override fun getNowPlayingMovies(): LiveData<Resource<List<MovieEntity>>> =
        object : NetworkBoundResource<List<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> =
                localDataSource.getNowPlayingMovies()

            override fun shouldFetch(data: List<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getNowPlayingMovies()

            override fun saveCallResult(data: List<MovieResponse>) {
                val listMovie = ArrayList<MovieEntity>()
                for (response in data) {
                    with(response) {
                        val movie = MovieEntity(
                            id = id,
                            title = title,
                            poster = posterPath,
                            0.0,
                            0,
                            "",
                            "",
                            false
                        )
                        listMovie.add(movie)
                    }
                    localDataSource.insertMovie(listMovie)
                }
            }

        }.asLiveData()


    override fun getDetailNowPlayingMovies(moviesId: Int): LiveData<Resource<MovieEntity>> =
        object : NetworkBoundResource<MovieEntity, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getDetailNowPlayingMovies(moviesId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data != null && data.duration == 0 && data.genre == "" && data.score == 0.0 && data.overview == ""

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> =
                remoteDataSource.getDetailNowPlayingMovies(moviesId)

            override fun saveCallResult(data: MovieDetailResponse) {
                with(data) {

                    val movie = MovieEntity(
                        id = id,
                        title = title,
                        poster = posterPath,
                        score = voteAverage,
                        duration = runtime,
                        genre = genres.first().name,
                        overview = overview,
                        isFav = false
                    )
                    localDataSource.updateMovies(movie, false)
                }
            }

        }.asLiveData()

    override fun getUpcomingMovies(): LiveData<Resource<List<UpComingMovieEntity>>> =
        object : NetworkBoundResource<List<UpComingMovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<UpComingMovieEntity>> =
                localDataSource.getUpcomingMovies()

            override fun shouldFetch(data: List<UpComingMovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getUpcomingMovies()

            override fun saveCallResult(data: List<MovieResponse>) {
                val listMovie = ArrayList<UpComingMovieEntity>()
                for (response in data) {
                    with(response) {
                        val movie = UpComingMovieEntity(
                            id = id,
                            title = title,
                            poster = posterPath,
                            years = releaseDate
                        )
                        listMovie.add(movie)
                    }
                    localDataSource.insertUpcomingMovie(listMovie)
                }
            }

        }.asLiveData()

    override fun getFavoriteMovies(): LiveData<List<MovieEntity>> =
        localDataSource.getFavoriteMovie()

    override fun setFavoriteMovies(movieEntity: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movieEntity, state)
        }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource, localDataSource, appExecutors)
            }
    }
}