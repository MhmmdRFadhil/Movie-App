package com.ryz.movie.ui.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.ryz.movie.R
import com.ryz.movie.core.data.source.local.entity.MovieEntity
import com.ryz.movie.core.data.source.local.entity.UpComingMovieEntity
import com.ryz.movie.core.utils.ZoomCenterLinearLayoutManager
import com.ryz.movie.core.vo.Status
import com.ryz.movie.databinding.ActivityMainBinding
import com.ryz.movie.ui.movie.all.AllMovieActivity
import com.ryz.movie.ui.movie.detail.DetailMovieActivity
import com.ryz.movie.ui.movie.detail.DetailViewModel
import com.ryz.movie.ui.movie.favorite.FavoriteActivity
import com.ryz.movie.ui.movie.nowplaying.MovieClickedCallback
import com.ryz.movie.ui.movie.nowplaying.MovieNowPlayingAdapter
import com.ryz.movie.ui.movie.nowplaying.MovieViewModel
import com.ryz.movie.ui.movie.upcoming.UpcomingMovieAdapter
import com.ryz.movie.ui.movie.upcoming.UpcomingMovieViewModel
import com.ryz.movie.ui.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieNowPlayingAdapter: MovieNowPlayingAdapter
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var upcomingMovieAdapter: UpcomingMovieAdapter
    private lateinit var upcomingViewModel: UpcomingMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSeeAll.setOnClickListener {
            startActivity(Intent(this@MainActivity, AllMovieActivity::class.java))
        }

        binding.cvFavorite.setOnClickListener {
            startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }

        setViewModelUpcomingMovie()
        setViewModelMovie()
    }

    private fun setViewModelUpcomingMovie() {
        val factory = ViewModelFactory.getInstance(this)
        upcomingViewModel = ViewModelProvider(this, factory)[UpcomingMovieViewModel::class.java]
        upcomingViewModel.getUpcomingMovies().observe(this) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressBar.root.visibility = View.VISIBLE
                        binding.cvFavorite.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.progressBar.root.visibility = View.GONE
                        binding.cvFavorite.visibility = View.VISIBLE
                        showRecyclerViewUpComingMovie(it.data?.sortedByDescending { sort ->
                            sort.id
                        })
                    }
                    Status.ERROR -> {
                        binding.progressBar.root.visibility = View.GONE
                        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun showRecyclerViewUpComingMovie(data: List<UpComingMovieEntity>?) {
        binding.rvComingSoon.apply {

            upcomingMovieAdapter = UpcomingMovieAdapter()
            setHasFixedSize(true)
            upcomingMovieAdapter.setUpcomingMovie(data)
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)

            adapter = upcomingMovieAdapter
        }
    }

    private fun setViewModelMovie() {
        val factory = ViewModelFactory.getInstance(this)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        movieViewModel.getNowPlayingMovies().observe(this) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressBar.root.visibility = View.VISIBLE
                        binding.cvFavorite.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.progressBar.root.visibility = View.GONE
                        binding.cvFavorite.visibility = View.VISIBLE
                        showRecyclerViewMovie(it.data)
                    }
                    Status.ERROR -> {
                        binding.progressBar.root.visibility = View.GONE
                        Toast.makeText(
                            this,
                            getString(R.string.error_message),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }
    }

    private fun showRecyclerViewMovie(data: List<MovieEntity>?) {
        binding.rvNowPlaying.apply {

            movieNowPlayingAdapter = MovieNowPlayingAdapter()
            setHasFixedSize(true)
            movieNowPlayingAdapter.setMovie(data)
            layoutManager =
                ZoomCenterLinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            adapter = movieNowPlayingAdapter
            val snapHelper = LinearSnapHelper()
            onFlingListener = null
            snapHelper.attachToRecyclerView(this)

            movieNowPlayingAdapter.setOnItemClickedCallback(object : MovieClickedCallback {
                override fun onItemClick(movieEntity: MovieEntity) {
                    val intent = Intent(this@MainActivity, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_ID, movieEntity.id)
                    intent.putExtra(
                        DetailMovieActivity.EXTRA_CATEGORY,
                        DetailViewModel.TYPE_NOW_PLAYING
                    )
                    startActivity(intent)
                }
            })

        }
    }
}