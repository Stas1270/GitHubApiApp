package com.stas1270.githubapi.ui.repo_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.stas1270.githubapi.R
import com.stas1270.githubapi.databinding.FragmentRepoDetailsBinding
import com.stas1270.githubapi.ui.base.extensions.repeatOnViewLifecycle
import com.stas1270.githubapi.ui.base.extensions.showErrorToast
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.utils.convertToUiDate
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class RepoDetailsFragment : Fragment() {

    private var _binding: FragmentRepoDetailsBinding? = null

    private val binding get() = _binding!!

    private val navigationArgs: RepoDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: RepoDetailViewModel

    override fun onAttach(context: Context) {
//        (context.applicationContext as GitHubApp).inject(this)
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
                if (it) showErrorToast()
            }
        }
    }

    private fun showDetails(model: RepoDetailedModel) {
        with(binding) {
            avatar.load(model.ownerAvatarUrl)
            repoName.text = model.name
            val createdAt = getString(
                R.string.created_at, convertToUiDate(
                    requireContext(),
                    model.createdAt
                )
            )
            repoCreatedAt.text = createdAt
            val countStars = getString(R.string.stars, model.stargazersCount)
            repoStargazersCount.text = countStars
            val updatedAt = getString(
                R.string.updated_at, convertToUiDate(
                    requireContext(),
                    model.updatedAt
                )
            )
            repoUpdatedAt.text = updatedAt
            val url = getString(R.string.see_more_at, model.htmlUrl)
            repoUrl.text = url
            val createdBy = getString(R.string.created_by, model.ownerLogin)
            ownerLogin.text = createdBy
            val description = getString(R.string.description, model.description)
            repoDescription.text = description
        }
    }

    private fun fetchRepoDetails() {
        val id = navigationArgs.repoId
        viewModel.getRepoDetails(id)
    }
}