package com.example.main.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.themoviedb.org/3/movie/popular?api_key=API_KEY&/language=en-US&page=1


interface SearchInterface {

    @GET("search/movie?api_key=$API_KEY")
    fun getMovies(@Query("query")query: String, @Query("include_adult")include_adult: Boolean,
                  @Query("language")language: String, @Query("page") page: Int): Call<Data>
}

object SearchService{
    val searchInterface: SearchInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        searchInterface= retrofit.create(SearchInterface::class.java)
    }
}