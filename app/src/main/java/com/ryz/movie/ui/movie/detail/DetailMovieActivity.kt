package com.ryz.movie.ui.movie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ryz.movie.R
import com.ryz.movie.core.data.source.local.entity.MovieEntity
import com.ryz.movie.core.utils.loadImageUrl
import com.ryz.movie.core.utils.simpleToolbar
import com.ryz.movie.core.vo.Status
import com.ryz.movie.databinding.ActivityDetailMovieBinding
import com.ryz.movie.ui.viewmodel.ViewModelFactory
import com.ryz.movie.ui.movie.detail.DetailViewModel.Companion.TYPE_NOW_PLAYING
import kotlin.math.abs

class DetailMovieActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var movieEntity: MovieEntity
    private var isMovieDetail: Boolean = false
    private var dataCategory: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        binding.progressBar.root.visibility = View.VISIBLE
        binding.fabAddFavorite.setOnClickListener(this)

        val extras = intent.extras
        if (extras != null) {
            val dataId = extras.getInt(EXTRA_ID)
            dataCategory = extras.getString(EXTRA_CATEGORY)

            if (dataCategory != null) {
                if (dataCategory == TYPE_NOW_PLAYING) {
                    isMovieDetail = true
                    viewModel.setMovie(dataId, dataCategory.toString())
                    viewModel.getDetailNowPlayingMovies().observe(this) {
                        when (it.status) {
                            Status.LOADING -> {
                                binding.progressBar.root.visibility = View.VISIBLE
                                binding.fabAddFavorite.visibility = View.GONE
                            }
                            Status.SUCCESS -> {
                                if (it.data != null) {
                                    binding.progressBar.root.visibility = View.GONE
                                    binding.fabAddFavorite.visibility = View.VISIBLE
                                    populateDetailMovie(it.data)
                                }
                            }
                            Status.ERROR -> {
                                binding.progressBar.root.visibility = View.GONE
                                Toast.makeText(
                                    this,
                                    getString(R.string.error_message),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }

        }

    }

    private fun populateDetailMovie(data: MovieEntity) {
        movieEntity = data
        setFavState(data.isFav)

        binding.apply {
            data.poster.let {
                posterDetail.loadImageUrl(it)
            }
            tvTitle.text = data.title
            tvGenre.text = data.genre
            tvDuration.text = convertDuration(data.duration)
            tvRating.text = getString(R.string.score, data.score.toString())
            tvSynopsis.text = convertOverview(data.overview)

            simpleToolbar(data.title, binding.toolbar.root, true)
        }
    }

    private fun convertDuration(duration: Int?): String {
        var tvDuration = ""

        duration?.let {
            val hours = it / 60
            val minutes = abs(it) % 60

            tvDuration = when {
                hours == 0 && minutes == 0 -> {
                    getString(R.string.unknown)
                }
                hours == 0 -> {
                    "$minutes m"
                }
                minutes == 0 -> {
                    "$hours h"
                }
                else -> {
                    "$hours h $minutes m"
                }
            }
        }
        return tvDuration
    }

    private fun convertOverview(overview: String?): String {
        var tvOverview = ""

        overview?.let {
            tvOverview = it.ifEmpty {
                getString(R.string.no_info)
            }
        }
        return tvOverview
    }

    private fun setFavState(fav: Boolean) {
        binding.apply {
            if (fav) fabAddFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            else fabAddFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.fabAddFavorite -> {
                if (isMovieDetail) {
                    if (!movieEntity.isFav)
                        Toast.makeText(
                            this,
                            getString(R.string.success_added_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            this,
                            getString(R.string.success_removed_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    viewModel.setFavorite(movieEntity)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_CATEGORY = "extra_category"
    }
}