package com.example.main.api

import com.example.main.api.Model.Pojo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Interface {
    @GET("search/movie")
    fun getMovie(
        @Query("input") text: String
    ): Call<Pojo>
}