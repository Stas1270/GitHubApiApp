package com.stas1270.githubapi.ui.repo_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.stas1270.githubapi.GitHubApp
import com.stas1270.githubapi.R
import com.stas1270.githubapi.databinding.FragmentRepoDetailsBinding
import com.stas1270.githubapi.ui.extensions.repeatOnViewLifecycle
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class RepoDetailsFragment : Fragment() {

    private var _binding: FragmentRepoDetailsBinding? = null

    private val binding get() = _binding!!

    private val navigationArgs: RepoDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: RepoDetailViewModel

    override fun onAttach(context: Context) {
        (context.applicationContext as GitHubApp).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchRepoDetails()
        repeatOnViewLifecycle {
            viewModel.viewState.collectLatest {
                showDetails(it)
            }
        }
        repeatOnViewLifecycle {
            viewModel.error.collectLatest {
                if (it) showError()
            }
        }
    }

    private fun showError() {
        Toast.makeText(this.requireContext(), R.string.toast_error, Toast.LENGTH_LONG).show()
    }

    private fun showDetails(model: RepoDetailedModel) {
        with(binding) {
            avatar.load(model.ownerAvatarUrl)
            repoName.text = model.name
            val createdAt = "Created at: ${convertToUiDate(model.createdAt)}"
            repoCreatedAt.text = createdAt
            val countStars = "Stars: ${model.stargazersCount}"
            repoStargazersCount.text = countStars
            val updatedAt = "Updated at: ${convertToUiDate(model.updatedAt)}"
            repoUpdatedAt.text = updatedAt
            val url = "See more at: ${model.htmlUrl}"
            repoUrl.text = url
            val createdBy = "Created by:\n${model.ownerLogin}"
            ownerLogin.text = createdBy
        }
    }

    private fun convertToUiDate(string: String) = string.substringBefore(
        "T",
        getString(R.string.who_knows)
    )

    private fun fetchRepoDetails() {
        val id = navigationArgs.repoId
        viewModel.getRepoDetails(id)
    }
}