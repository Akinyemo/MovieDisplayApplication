package com.example.android.githubsearchwithnavigation.api

import android.util.Log
import com.example.android.githubsearchwithnavigation.data.MovieDetails
import com.example.android.githubsearchwithnavigation.data.MovieSearchResults
import com.example.android.githubsearchwithnavigation.data.Video
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Path

interface VideoService {
    @GET("3/movie/{movie_id}/videos")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String
    ) : List<Video>

    //Changing this file to query for a specific movie
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/"
        fun create() : VideoService {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            Log.d("sauter_tag", "MovieService moshi: " + moshi.toString())
            Log.d("sauter_tag", "MovieService retrofit: " + retrofit.create(SearchMovieService::class.java).toString())
            Log.d("sauter_tag", "Retrofit base URL: " + retrofit.baseUrl() )

            return retrofit.create(VideoService::class.java)
        }
    }
}