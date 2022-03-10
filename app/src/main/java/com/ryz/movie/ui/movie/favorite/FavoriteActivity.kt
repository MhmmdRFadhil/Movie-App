package com.ryz.movie.ui.movie.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ryz.movie.R
import com.ryz.movie.core.data.source.local.entity.MovieEntity
import com.ryz.movie.core.utils.simpleToolbar
import com.ryz.movie.databinding.ActivityFavoriteBinding
import com.ryz.movie.ui.movie.all.AllMovieActivity
import com.ryz.movie.ui.viewmodel.ViewModelFactory
import com.ryz.movie.ui.movie.detail.DetailMovieActivity
import com.ryz.movie.ui.movie.detail.DetailViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteMovieAdapter: FavoriteMovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        simpleToolbar(getString(R.string.favorite), binding.toolbar.root, true)

        setupViewModel()
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        favoriteMovieAdapter = FavoriteMovieAdapter()
        favoriteViewModel.getFavMovie().observe(this) {
            if (it.isEmpty()) {
                emptyData()
            } else {
                showRecyclerView(it)
            }
        }
        favoriteMovieAdapter.setOnItemClickedCallback(object : FavoriteMovieClickedCallback {
            override fun onItemClick(movieEntity: MovieEntity) {
                val intent = Intent(this@FavoriteActivity, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_ID, movieEntity.id)
                intent.putExtra(
                    DetailMovieActivity.EXTRA_CATEGORY,
                    DetailViewModel.TYPE_NOW_PLAYING
                )
                startActivity(intent)
            }
        })
    }

    private fun emptyData() {
        binding.apply {
            rvFavorite.visibility = View.GONE
            layoutEmptyData.let {
                it.tvTitleEmptyData.text = getString(R.string.title_result_empty_data_movie)
                it.tvSubTitleEmptyData.text = getString(R.string.subtitle_result_empty_data_movie)
                it.root.visibility = View.VISIBLE
                it.btnFavorite.setOnClickListener {
                    startActivity(Intent(this@FavoriteActivity, AllMovieActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun showRecyclerView(data: List<MovieEntity>?) {
        binding.rvFavorite.apply {
            setHasFixedSize(true)
            favoriteMovieAdapter.setFavMovie(data)
            layoutManager = GridLayoutManager(this@FavoriteActivity, 2)
            adapter = favoriteMovieAdapter
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
}