package com.example.android.githubsearchwithnavigation.data

import com.example.android.githubsearchwithnavigation.api.MovieService
import com.example.android.githubsearchwithnavigation.api.SimilarMovieService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MovieRepository(
    private val service: MovieService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun get_movie_details(
        api_key: String,
        movie_id: String
    ): Result<MovieDetails> =
        withContext(ioDispatcher) {
            try {
                val movie_details = service.getMovieDetails(movie_id, api_key)
                Result.success(movie_details)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}