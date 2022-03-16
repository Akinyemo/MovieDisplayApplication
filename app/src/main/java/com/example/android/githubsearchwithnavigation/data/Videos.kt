package com.example.android.githubsearchwithnavigation.data

import com.squareup.moshi.Json
import java.io.Serializable

class Videos(
    @Json(name="results")
    val videos: List<Video>
)

class Video(

    @Json(name="name") val name: String,
    @Json(name="key") val key: String,
    @Json(name="site") val site: String,
    @Json(name="size") val size: Integer,
    @Json(name="type") val type: String,
    @Json(name="official") val official: Boolean,
    @Json(name="published_at") val published_at: String,
    @Json(name="id") val id: String

) : Serializable