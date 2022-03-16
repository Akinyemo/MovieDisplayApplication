package com.example.android.githubsearchwithnavigation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.githubsearchwithnavigation.R
import com.example.android.githubsearchwithnavigation.data.Movie
import kotlin.reflect.KFunction0

class MovieListAdapter(private val onMovieClick: (Movie) -> Unit)
    : RecyclerView.Adapter<MovieListAdapter.movieViewHolder>() {
    var movieList = listOf<Movie>()

    fun updateMovieList(newMovieList: List<Movie>?) {
        movieList = newMovieList ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = movieList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): movieViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        return movieViewHolder(itemView, onMovieClick)
    }

    override fun onBindViewHolder(holder: movieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    class movieViewHolder(itemView: View, val onClick: (Movie) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        private val nameTV: TextView = itemView.findViewById(R.id.tv_name)
        private val descTV: TextView = itemView.findViewById(R.id.tv_repo_description)
        private var curentMovie: Movie? = null

        init {
            itemView.setOnClickListener {
                curentMovie?.let(onClick)
            }
        }

        fun bind(movie: Movie) {
            curentMovie = movie
            nameTV.text = movie.name
            descTV.text =  "Description: " + movie.description
        }
    }
}