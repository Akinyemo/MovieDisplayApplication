package com.example.android.githubsearchwithnavigation.data

import com.example.android.githubsearchwithnavigation.api.MovieService
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
        api_key: String
    ): Result<List<Movie>> =
        withContext(ioDispatcher) {
            try {
                val movies_list = service.searchMovies(api_key)
                Result.success(movies_list.items)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}