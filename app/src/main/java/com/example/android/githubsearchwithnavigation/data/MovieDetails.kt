package com.example.android.githubsearchwithnavigation.data

import com.squareup.moshi.Json
import java.io.Serializable

class MovieDetails(

    @Json(name="adult") val adult_bool: Boolean,
    @Json(name="homepage") val homepage: String,
    @Json(name="id") val id: String,
    @Json(name="imdb_id") val imdb_id: String,
    @Json(name="overview") val overview: String,
    @Json(name="title") val title: String,
    @Json(name="vote_average") val vote_average: Double

) : Serializable