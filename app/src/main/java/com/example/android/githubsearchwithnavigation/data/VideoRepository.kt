package com.example.android.githubsearchwithnavigation.data

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
        movie_id: Integer,
        api_key: String
    ): Result<Videos> =
        withContext(ioDispatcher) {
            try {
                val videos = service.getVideos(movie_id, api_key)
                Result.success(videos)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}