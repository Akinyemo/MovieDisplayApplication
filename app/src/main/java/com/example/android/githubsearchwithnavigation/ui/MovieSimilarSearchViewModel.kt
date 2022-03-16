package com.example.android.githubsearchwithnavigation.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.githubsearchwithnavigation.api.SearchMovieService
import com.example.android.githubsearchwithnavigation.api.SimilarMovieService
import com.example.android.githubsearchwithnavigation.data.LoadingStatus
import com.example.android.githubsearchwithnavigation.data.Movie
import com.example.android.githubsearchwithnavigation.data.SearchMoviesRepository
import com.example.android.githubsearchwithnavigation.data.SimilarMoviesRepository
import kotlinx.coroutines.launch

class MovieSimilarSearchViewModel : ViewModel() {
    private val repository = SimilarMoviesRepository(SimilarMovieService.create())

    private val _searchResults = MutableLiveData<List<Movie>?>(null)
    val searchResults: LiveData<List<Movie>?> = _searchResults

    private val _loadingStatus = MutableLiveData(LoadingStatus.SUCCESS)
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    fun loadSearchResults(
        query: String,
        api_key: String,
        include_adult: Boolean
    ) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val result = repository.loadRepositoriesSearch(api_key,query, include_adult)
            _searchResults.value = result.getOrNull()
            _loadingStatus.value = when (result.isSuccess) {
                true ->  LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }
}