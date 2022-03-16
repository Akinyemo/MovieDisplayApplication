package com.example.android.githubsearchwithnavigation.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.githubsearchwithnavigation.R
import com.example.android.githubsearchwithnavigation.data.Movie
import com.google.android.material.snackbar.Snackbar

const val EXTRA_MOVIE_REPO = "com.example.android.githubsearchwithnavigation.MovieRepo"

class MovieDetailFragment : Fragment(R.layout.movie_detail) {
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        view.findViewById<TextView>(R.id.tv_repo_name).text = args.movie.name
        view.findViewById<TextView>(R.id.tv_repo_description).text = args.movie.description
        view.findViewById<ImageView>(R.id.iv_rating_1).setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_rate_36, null))
        val searchBtn: Button = view.findViewById(R.id.btn_similar)
        searchBtn.setOnClickListener {
            onSimilarResultsFound(args.movie)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.activity_repo_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_share -> {
                shareMovie()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //btn_similar
    /**
     * This method toggles the state of the bookmark icon in the top app bar whenever the user
     * clicks it.
     */

    private fun viewMovieOnIMDb() {

        // This needs done based on imdb id - api doesn't return a URL
//        val intent: Intent = Uri.parse(args.movie.url).let {
//            Intent(Intent.ACTION_VIEW, it)
//        }
//        try {
//            startActivity(intent)
//        } catch (e: ActivityNotFoundException) {
//            Snackbar.make(
//                requireView(),
//                R.string.action_view_repo_error,
//                Snackbar.LENGTH_LONG
//            ).show()
//        }
    }

    private fun shareMovie() {
        val text = getString(R.string.share_text, args.movie.name)//, args.movie.url)
        val intent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, null))
    }
    private fun onSimilarResultsFound(movie: Movie) {
        val directions = MovieDetailFragmentDirections.navigateToMovieSimilarResults(movie)
        findNavController().navigate(directions)
    }
}