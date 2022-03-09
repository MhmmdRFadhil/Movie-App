package com.ryz.movie.core.data.source.remote.network

import com.ryz.movie.core.data.source.remote.StatusResponse

class ApiResponse<T>(val status: StatusResponse, val body: T, val message: String?) {
    companion object {
        fun <T> success(body: T): ApiResponse<T> = ApiResponse(StatusResponse.SUCCESS, body, null)
    }
}