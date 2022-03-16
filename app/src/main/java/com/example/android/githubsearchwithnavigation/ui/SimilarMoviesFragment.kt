package com.example.android.githubsearchwithnavigation.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.githubsearchwithnavigation.R
import com.example.android.githubsearchwithnavigation.data.LoadingStatus
import com.example.android.githubsearchwithnavigation.data.Movie
import com.google.android.material.progressindicator.CircularProgressIndicator

class SimilarMoviesFragment : Fragment(R.layout.movie_similar_search_results) {
    private val TAG = "MovieSimilarSearchResultFragment"

        private val movieListAdapter = MovieListAdapter(::onMovieClick)
        private val viewModel: MovieSimilarSearchViewModel by viewModels()
        private val args: SimilarMoviesFragmentArgs by navArgs()

        private lateinit var searchResultsListRV: RecyclerView
        private lateinit var searchErrorTV: TextView
        private lateinit var loadingIndicator: CircularProgressIndicator
        private lateinit var noSearchResults: TextView

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            //val movieListAdapter = MovieListAdapter(::onMovieClick,requireContext())

            searchResultsListRV = view.findViewById(R.id.rv_similar_search_results)
            searchErrorTV = view.findViewById(R.id.tv_search_error)
            loadingIndicator = view.findViewById(R.id.loading_indicator)
            noSearchResults = view.findViewById(R.id.tv_search_no_results)

            searchResultsListRV.layoutManager = LinearLayoutManager(requireContext())
            searchResultsListRV.setHasFixedSize(true)

            searchResultsListRV.adapter = movieListAdapter

            viewModel.searchResults.observe(viewLifecycleOwner) { searchResults ->
                movieListAdapter.updateMovieList(searchResults)
            }

            viewModel.loadingStatus.observe(viewLifecycleOwner) { uiState ->
                when (uiState) {
                    LoadingStatus.LOADING -> {
                        loadingIndicator.visibility = View.VISIBLE
                        searchResultsListRV.visibility = View.INVISIBLE
                        searchErrorTV.visibility = View.INVISIBLE
                    }
                    LoadingStatus.ERROR -> {
                        loadingIndicator.visibility = View.INVISIBLE
                        searchResultsListRV.visibility = View.INVISIBLE
                        searchErrorTV.visibility = View.VISIBLE
                    }
                    else -> {
                        loadingIndicator.visibility = View.INVISIBLE
                        if(movieListAdapter.itemCount > 0) {
                            searchResultsListRV.visibility = View.VISIBLE
                        }
                        else{
                            noSearchResults.visibility = View.VISIBLE
                        }
                        searchErrorTV.visibility = View.INVISIBLE
                    }
                }
            }
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
            if (!TextUtils.isEmpty(args.movie.id.toString())) {
                val api_key = "bba8404a924c32175d01bd606efbb093"
                Log.d("sauter_tag", "Id : " + args.movie.id.toString() )

                viewModel.loadSearchResults(args.movie.id.toString(), api_key, sharedPrefs.getBoolean("pref_include_adult_key",true))
            }

        }

    private fun onMovieClick(movie: Movie) {
        val directions = SimilarMoviesFragmentDirections.navigateBackToMovieDetail(movie, 16)
        findNavController().navigate(directions)
    }
}