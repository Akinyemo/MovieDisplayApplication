package com.example.android.githubsearchwithnavigation.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.githubsearchwithnavigation.R
import com.google.android.material.progressindicator.CircularProgressIndicator

class MovieSearchFragment : Fragment(R.layout.movie_search) {

    private val TAG = "MovieSearchFragment"

    private lateinit var searchBoxET: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBoxET = view.findViewById(R.id.et_search_box)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val searchBtn: Button = view.findViewById(R.id.btn_search)
        searchBtn.setOnClickListener {
            val query = searchBoxET.text.toString()
            onResultsFound(query)
        }
    }


    private fun onResultsFound(results: String) {
        val directions = MovieSearchFragmentDirections.navigateToSearchResults(results)
            findNavController().navigate(directions)
    }
}