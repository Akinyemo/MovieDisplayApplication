package com.example.android.githubsearchwithnavigation.data

import com.squareup.moshi.Json

data class MovieSearchResults(

    @Json(name = "results")
    val items: List<Movie>
)