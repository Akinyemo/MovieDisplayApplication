package com.example.android.githubsearchwithnavigation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.githubsearchwithnavigation.R
import com.example.android.githubsearchwithnavigation.data.Movie

class MovieListAdapter(private val onMovieClick: (Movie) -> Unit,context : Context)
    : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    var movieList = listOf<Movie>()
    val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
    var limit = sharedPrefs.getInt("pref_no_results_key",10)
    var lowestRating = sharedPrefs.getInt("pref_min_rating", 0)
    fun updateMovieList(newMovieList: List<Movie>?) {

        if (newMovieList != null) {
            movieList = newMovieList.filterNot{it.rating <= lowestRating.toDouble()}

        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if(movieList.size < limit){
            return movieList.size
        }
        else{
            return limit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(itemView, onMovieClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class MovieViewHolder(itemView: View, val onClick: (Movie) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        private val nameTV: TextView = itemView.findViewById(R.id.tv_name)
        private val descTV: TextView = itemView.findViewById(R.id.tv_repo_description)
        private var currentMovie: Movie? = null

        init {
            itemView.setOnClickListener {
                currentMovie?.let(onClick)
            }
        }

        fun bind(movie: Movie) {
            currentMovie = movie
            nameTV.text = movie.name
            descTV.text =  "Description: " + movie.description
        }
    }
}