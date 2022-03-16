package com.example.android.githubsearchwithnavigation.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.githubsearchwithnavigation.api.MovieService
import com.example.android.githubsearchwithnavigation.api.SearchMovieService
import com.example.android.githubsearchwithnavigation.api.VideoService
import com.example.android.githubsearchwithnavigation.data.*
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {
    private val repository = VideoRepository(VideoService.create())

    private val _searchResults = MutableLiveData<List<Video>>(null)
    val searchResults: LiveData<List<Video>?> = _searchResults

    private val _loadingStatus = MutableLiveData(LoadingStatus.SUCCESS)
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    fun loadVideos(
        movie_id: String,
        api_key: String,
    ) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val result = repository.getVideos(movie_id,api_key)
            _searchResults.value = result.getOrNull()
            _loadingStatus.value = when (result.isSuccess) {
                true ->  LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
            Log.d("sauter_tag", "ViewModel Results: " + result.toString())
        }
    }
}