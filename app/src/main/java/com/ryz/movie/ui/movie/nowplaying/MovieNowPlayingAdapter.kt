package com.ryz.movie.ui.movie.nowplaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ryz.movie.core.data.source.local.entity.MovieEntity
import com.ryz.movie.core.utils.loadImageUrl
import com.ryz.movie.databinding.ItemRowNowPlayingBinding

class MovieNowPlayingAdapter: RecyclerView.Adapter<MovieNowPlayingAdapter.ViewHolder>() {

    private var listMovie = ArrayList<MovieEntity>()
    private lateinit var onItemClickedCallback: MovieClickedCallback

    fun setOnItemClickedCallback(onItemClickedCallback: MovieClickedCallback) {
        this.onItemClickedCallback = onItemClickedCallback
    }

    fun setMovie(movieEntity: List<MovieEntity>?) {
        if (movieEntity == null) return
        this.listMovie.clear()
        this.listMovie.addAll(movieEntity)
    }

    inner class ViewHolder(private val binding: ItemRowNowPlayingBinding) :
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
            ItemRowNowPlayingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = 5
}

interface MovieClickedCallback {
    fun onItemClick(movieEntity: MovieEntity)
}