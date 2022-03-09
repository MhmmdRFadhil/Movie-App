package com.ryz.movie.ui.movie.all

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ryz.movie.R
import com.ryz.movie.core.data.source.local.entity.MovieEntity
import com.ryz.movie.core.utils.simpleToolbar
import com.ryz.movie.core.vo.Status
import com.ryz.movie.databinding.ActivityAllMovieBinding
import com.ryz.movie.ui.movie.detail.DetailMovieActivity
import com.ryz.movie.ui.movie.detail.DetailViewModel
import com.ryz.movie.ui.viewmodel.ViewModelFactory
import com.ryz.movie.ui.movie.nowplaying.MovieViewModel

class AllMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllMovieBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var allMovieAdapter: AllMovieAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        simpleToolbar(getString(R.string.all_movie), binding.toolbar.root, true)

        setViewModel()
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        movieViewModel.getNowPlayingMovies().observe(this) {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        binding.progressBar.root.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressBar.root.visibility = View.GONE
                        showRecyclerView(it.data)
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

    private fun showRecyclerView(data: List<MovieEntity>?) {
        binding.rvAllMovie.apply {
            allMovieAdapter = AllMovieAdapter()
            setHasFixedSize(true)
            allMovieAdapter.setMovie(data)
            layoutManager = GridLayoutManager(this@AllMovieActivity, 2)
            adapter = allMovieAdapter

            allMovieAdapter.setOnItemClickedCallback(object : AllMovieClickedCallback {
                override fun onItemClick(movieEntity: MovieEntity) {
                    val intent = Intent(this@AllMovieActivity, DetailMovieActivity::class.java)
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