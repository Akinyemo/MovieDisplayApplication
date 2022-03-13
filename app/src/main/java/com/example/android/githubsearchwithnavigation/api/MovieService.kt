package com.example.android.githubsearchwithnavigation.api

import com.example.android.githubsearchwithnavigation.data.MovieSearchResults
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface MovieService {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String? = "stars"
    ) : MovieSearchResults

    //Changing this file to query for a specific movie
    companion object {
        private const val BASE_URL = "https://api.github.com/"
        fun create() : MovieService {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            return retrofit.create(MovieService::class.java)
        }
    }
}