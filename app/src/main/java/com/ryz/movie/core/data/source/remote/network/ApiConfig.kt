package com.ryz.movie.core.data.source.remote.network

import com.ryz.movie.core.utils.NetworkInfo.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private val httpClient = OkHttpClient.Builder().build()
    private val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder().apply {
            client(httpClient)
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }
    }

    val instance: ApiServices by lazy {
        retrofit.build().create(ApiServices::class.java)
    }
}