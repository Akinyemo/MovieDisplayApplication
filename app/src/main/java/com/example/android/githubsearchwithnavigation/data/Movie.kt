package com.example.android.githubsearchwithnavigation.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

data class Movie(
    @Json(name = "original_title")
    val name: String,

    @Json(name = "id")
    val id: String,

    //Gonna be under overview for api
    @Json(name="overview")
    val description: String,

    @Json(name = "vote_average")
    val rating: Double,

    @Json(name="adult")
    val adult: Boolean

) : Serializable
