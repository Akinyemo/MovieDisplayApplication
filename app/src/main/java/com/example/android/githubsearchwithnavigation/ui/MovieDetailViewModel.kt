package com.example.android.githubsearchwithnavigation.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.githubsearchwithnavigation.api.MovieService
import com.example.android.githubsearchwithnavigation.api.SearchMovieService
import com.example.android.githubsearchwithnavigation.data.*
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {
    private val repository = MovieRepository(MovieService.create())

    private val _searchResults = MutableLiveData<MovieDetails>(null)
    val searchResults: LiveData<MovieDetails?> = _searchResults

    private val _loadingStatus = MutableLiveData(LoadingStatus.SUCCESS)
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    fun loadMovieDetails(
        movie_id: String,
        api_key: String,
    ) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val result = repository.get_movie_details(movie_id,api_key)
            _searchResults.value = result.getOrNull()
            _loadingStatus.value = when (result.isSuccess) {
                true ->  LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
            Log.d("sauter_tag", "ViewModel Results: " + result.toString())
        }
    }
}