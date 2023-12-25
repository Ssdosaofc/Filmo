package com.example.main.api


import com.google.gson.annotations.SerializedName

data class Result(

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("id")
    val id: Int

)