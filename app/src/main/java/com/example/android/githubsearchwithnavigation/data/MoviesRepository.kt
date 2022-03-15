package com.example.android.githubsearchwithnavigation.data

import android.text.TextUtils
import com.example.android.githubsearchwithnavigation.api.MovieService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MoviesRepository(
    private val service: MovieService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadRepositoriesSearch(
        query: String,
        api_key: String
    ): Result<List<Movie>> =
        withContext(ioDispatcher) {
            try {
                val movies_list = service.searchMovies(query, api_key)
                Result.success(movies_list.items)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}