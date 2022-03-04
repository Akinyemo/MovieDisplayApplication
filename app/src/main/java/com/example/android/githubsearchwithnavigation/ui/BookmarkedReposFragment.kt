package com.example.android.githubsearchwithnavigation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.githubsearchwithnavigation.R
import com.example.android.githubsearchwithnavigation.data.GitHubRepo

class BookmarkedReposFragment : Fragment(R.layout.bookmarked_repos) {
    private val repoListAdapter = GitHubRepoListAdapter(::onGitHubRepoClick)
    private lateinit var bookmarkedReposRV: RecyclerView

    private val viewModel: BookmarkedReposViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarkedReposRV = view.findViewById(R.id.rv_bookmarked_repos)
        bookmarkedReposRV.layoutManager = LinearLayoutManager(requireContext())
        bookmarkedReposRV.setHasFixedSize(true)
        bookmarkedReposRV.adapter = this.repoListAdapter

        viewModel.bookmarkedRepos.observe(viewLifecycleOwner) { bookmarkedRepos ->
            repoListAdapter.updateRepoList(bookmarkedRepos)
        }
    }

    private fun onGitHubRepoClick(repo: GitHubRepo) {
        val directions = BookmarkedReposFragmentDirections.navigateToRepoDetail(repo, 32)
        findNavController().navigate(directions)
    }
}