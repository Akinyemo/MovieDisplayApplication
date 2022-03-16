package com.example.android.githubsearchwithnavigation.data

import android.util.Log
import com.example.android.githubsearchwithnavigation.api.SearchMovieService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class SearchMoviesRepository(
    private val service: SearchMovieService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadMoviesSearch(
        query: String,
        api_key: String,
        include_adult: Boolean
    ): Result<List<Movie>> =
        withContext(ioDispatcher) {
            try {
                Log.d("sauter_tag", "MoviesRepository Query: " + query)
                Log.d("sauter_tag", "MoviesRepository key: " + api_key)
                Log.d("sauter_tag", "MoviesRepository adult: " + include_adult.toString())
                val movies_list = service.searchMovies(query, api_key, include_adult.toString())
                Result.success(movies_list.items)
            } catch (e: Exception) {
                Log.d("sauter_tag", "MoviesRepository Exception")
                Result.failure(e)
            }
        }
}