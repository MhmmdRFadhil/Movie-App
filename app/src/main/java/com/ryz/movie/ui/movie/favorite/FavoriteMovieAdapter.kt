package com.ryz.movie.ui.movie.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ryz.movie.core.data.source.local.entity.MovieEntity
import com.ryz.movie.core.utils.loadImageUrl
import com.ryz.movie.databinding.ItemRowAllMovieBinding

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>() {

    private var listMovie = ArrayList<MovieEntity>()
    private lateinit var onItemClickedCallback: FavoriteMovieClickedCallback

    fun setOnItemClickedCallback(onItemClickedCallback: FavoriteMovieClickedCallback) {
        this.onItemClickedCallback = onItemClickedCallback
    }

    fun setFavMovie(movieEntity: List<MovieEntity>?) {
        if (movieEntity == null) return
        this.listMovie.clear()
        this.listMovie.addAll(movieEntity)
    }

    inner class ViewHolder(private val binding: ItemRowAllMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieEntity: MovieEntity) {
            with(binding) {
                tvTitleDetail.text = movieEntity.title
                movieEntity.poster.let {
                    imgPosterItem.loadImageUrl(it)
                }
            }
            itemView.setOnClickListener {
                onItemClickedCallback.onItemClick(movieEntity)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemRowAllMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovie.size
}

interface FavoriteMovieClickedCallback {
    fun onItemClick(movieEntity: MovieEntity)
}