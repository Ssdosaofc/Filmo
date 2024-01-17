package com.example.main.api


import com.google.gson.annotations.SerializedName

data class Retrieve(

    val overview: String="",
    val poster: String="",
    val title: String="",
    val movieID: String="",
    var lang: String="",
    var pop: String= "",
    var gen: Int = 0,
    var isFavorite: Boolean = false
)