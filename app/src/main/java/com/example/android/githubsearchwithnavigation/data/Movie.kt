package com.example.android.githubsearchwithnavigation.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.io.Serializable

@Entity
data class GitHubRepo(
    @Json(name = "full_name")
    @PrimaryKey
    val name: String,

    val description: String,

    @Json(name = "html_url")
    val url: String,

    @Json(name = "stargazers_count")
    val stars: Int
) : Serializable


data class Movie(
    //@Json(name = "full_name")
    val name: String,

    //Gonna be under overview for api
    val description: String,

    //@Json(name = "html_url")
    //gonna be under what I assume is vote_average
    val rating: Double,

) : Serializable
