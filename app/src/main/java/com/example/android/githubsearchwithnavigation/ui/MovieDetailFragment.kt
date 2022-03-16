package com.example.android.githubsearchwithnavigation.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.githubsearchwithnavigation.R
import com.example.android.githubsearchwithnavigation.data.Movie
import com.example.android.githubsearchwithnavigation.data.Video
import com.example.android.githubsearchwithnavigation.data.Videos
import com.google.android.material.snackbar.Snackbar
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import org.w3c.dom.Text


const val EXTRA_MOVIE_REPO = "com.example.android.githubsearchwithnavigation.MovieRepo"

class MovieDetailFragment : Fragment(R.layout.movie_detail), YouTubePlayer.OnInitializedListener{
    private val args: MovieDetailFragmentArgs by navArgs()
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private val videoViewModel: VideoViewModel by viewModels()
    val api_key = "bba8404a924c32175d01bd606efbb093"
    var imdbId: String? = null
    var youtubePreviewId: String? = null
    var youtubePlayerFragment: YouTubePlayerSupportFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        //https://spin.atomicobject.com/2019/06/24/embedding-youtube-videos-in-android-applications/
        //youtubePlayerFragment = childFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerFragment?
        var vote_count: Double?

        movieDetailViewModel.loadMovieDetails(args.movie.id, api_key)
        videoViewModel.loadVideos(args.movie.id, api_key)

        movieDetailViewModel.searchResults.observe(viewLifecycleOwner) { movieDetails ->
            imdbId = movieDetails?.imdb_id
            vote_count = movieDetails?.vote_average
            Log.d("Test", vote_count.toString())

            if (vote_count !== null) {
                view.findViewById<ImageView>(R.id.iv_rating_1).setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_rate_36, null))
                if (vote_count!! >= 2.0) view.findViewById<ImageView>(R.id.iv_rating_2).setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_rate_36, null))
                if (vote_count!! >= 4.0) view.findViewById<ImageView>(R.id.iv_rating_3).setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_rate_36, null))
                if (vote_count!! >= 6.0) view.findViewById<ImageView>(R.id.iv_rating_4).setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_rate_36, null))
                if (vote_count!! >= 8.0) view.findViewById<ImageView>(R.id.iv_rating_5).setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_rate_36, null))
            }
        }

        videoViewModel.searchResults.observe(viewLifecycleOwner) { videos ->
            val youtubePreview: Video? = videos?.videos?.find { video ->
                video.site == "YouTube"
                video.type == "Trailer"
            }

            youtubePreviewId = youtubePreview?.key
            if (videos !== null) {
                youtubePlayerFragment =
                    childFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerSupportFragment?
                youtubePlayerFragment?.initialize("AIzaSyAooaLLzPeOb7pElBd13WsCJVyGvNljwZo", this)
            }
        }

        view.findViewById<TextView>(R.id.tv_repo_name).text = args.movie.name
        view.findViewById<TextView>(R.id.tv_repo_description).text = args.movie.description
        view.findViewById<TextView>(R.id.tv_view_on_imdb).setOnClickListener {
            viewMovieOnIMDb()
        }

        view.findViewById<TextView>(R.id.tv_view_on_youtube).setOnClickListener {
            viewMovieOnYouTube()
        }
            view.findViewById<ImageView>(R.id.iv_rating_1)
                .setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_rate_36, null))
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

        if (imdbId !== null) {
            // This needs done based on imdb id - api doesn't return a URL
            val intent: Intent = Uri.parse("https://www.imdb.com/title/${imdbId}").let {
                Intent(Intent.ACTION_VIEW, it)
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Snackbar.make(
                    requireView(),
                    R.string.action_view_repo_error,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun viewMovieOnYouTube() {

        // This needs done based on imdb id - api doesn't return a URL
        val intent: Intent = Uri.parse("https://www.youtube.com/watch?v=${youtubePreviewId}").let {
            Intent(Intent.ACTION_VIEW, it)
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Snackbar.make(
                requireView(),
                R.string.action_view_repo_error,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun shareMovie() {
        val text = getString(R.string.share_text, args.movie.name, "https://www.imdb.com/title/${imdbId}")
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

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        p1?.cueVideo(youtubePreviewId)
        p1?.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Log.e("YoutubePlayerFragment", p1!!.name)
    }
}