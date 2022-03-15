package com.example.android.githubsearchwithnavigation.api

import com.example.android.githubsearchwithnavigation.data.MovieSearchResults
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface SimilarMovieService {
    @GET("3/search/movie/226979/similar")
    suspend fun searchMovies(
        @Query("api_key") key: String
    ) : MovieSearchResults

    //Changing this file to query for a specific movie
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/"
        fun create() : SimilarMovieService {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(SimilarMovieService::class.java)
        }
    }
}