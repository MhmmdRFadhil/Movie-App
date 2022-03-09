package com.ryz.movie.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenreMovieDetail(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)
