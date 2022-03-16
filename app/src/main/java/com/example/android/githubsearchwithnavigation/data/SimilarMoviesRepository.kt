package com.example.android.githubsearchwithnavigation.data

import android.util.Log
import com.example.android.githubsearchwithnavigation.api.SimilarMovieService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class SimilarMoviesRepository(
    private val service: SimilarMovieService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadRepositoriesSearch(
        api_key: String,
        movie_id: String,
        include_adult: Boolean
    ): Result<List<Movie>> =
        withContext(ioDispatcher) {
            try {
                val movies_list = service.getSimilarMovies(movie_id, api_key, include_adult.toString())
                Result.success(movies_list.items)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}