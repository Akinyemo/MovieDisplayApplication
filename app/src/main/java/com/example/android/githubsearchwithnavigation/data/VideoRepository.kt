package com.example.android.githubsearchwithnavigation.data

import com.example.android.githubsearchwithnavigation.api.MovieService
import com.example.android.githubsearchwithnavigation.api.SimilarMovieService
import com.example.android.githubsearchwithnavigation.api.VideoService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class VideoRepository(
    private val service: VideoService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getVideos(
        movie_id: String,
        api_key: String
    ): Result<List<Video>> =
        withContext(ioDispatcher) {
            try {
                val videos = service.getMovieDetails(movie_id, api_key)
                Result.success(videos)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}