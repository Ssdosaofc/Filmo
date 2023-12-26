package com.example.main.api


import com.google.gson.annotations.SerializedName

data class Retrieve(

    val overview: String="",
    val poster: String="",
    val title: String="",
    val movieID: String="",
    var isFavorite: Boolean = false
)