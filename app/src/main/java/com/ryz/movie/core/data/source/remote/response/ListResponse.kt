package com.ryz.movie.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @field:SerializedName("results")
    val result: List<T>? = null
)
