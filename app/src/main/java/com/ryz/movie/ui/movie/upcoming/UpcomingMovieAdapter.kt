package com.ryz.movie.ui.movie.upcoming

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ryz.movie.R
import com.ryz.movie.core.data.source.local.entity.UpComingMovieEntity
import com.ryz.movie.core.utils.loadImageUrl
import com.ryz.movie.databinding.ItemRowUpComingBinding

class UpcomingMovieAdapter: RecyclerView.Adapter<UpcomingMovieAdapter.ViewHolder>() {

    private var listMovie = ArrayList<UpComingMovieEntity>()
    private lateinit var context: Context

    fun setUpcomingMovie(upComingMovieEntity: List<UpComingMovieEntity>?) {
        if (upComingMovieEntity == null) return
        this.listMovie.clear()
        this.listMovie.addAll(upComingMovieEntity)
    }

    inner class ViewHolder(private val binding: ItemRowUpComingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(upComingMovieEntity: UpComingMovieEntity) {
            with(binding) {
                tvTitleDetail.text = upComingMovieEntity.title
                tvTitleYear.text = convertYears(upComingMovieEntity.years)
                upComingMovieEntity.poster.let {
                    imgPosterItem.loadImageUrl(it)
                }
            }
        }
    }

    private fun convertYears(years: String?): String {
        val date = years?.split("-")
        return date?.get(0) ?: context.getString(R.string.unknown)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemRowUpComingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = 7
}