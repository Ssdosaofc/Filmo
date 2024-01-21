package com.example.main.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.themoviedb.org/3/movie/popular?api_key=API_KEY&/language=en-US&page=1

const val API_KEY = "51e881caaa8c24e018f35bd8fc72136a"
const val BASE_URL= "https://api.themoviedb.org/3/"

interface MovieInterface {

    @GET("movie/popular?api_key=$API_KEY")
    fun getMovies(@Query("page") page: Int): Call<Data>
}

object MovieService{
    val movieInterface: MovieInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        movieInterface= retrofit.create(MovieInterface::class.java)
    }
}