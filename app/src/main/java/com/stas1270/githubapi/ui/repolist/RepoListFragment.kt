package com.stas1270.githubapi.ui.repolist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.stas1270.githubapi.GitHubApp
import com.stas1270.githubapi.databinding.FragmentRepoListBinding
import com.stas1270.githubapi.ui.base.extensions.hideSoftKeyboard
import com.stas1270.githubapi.ui.base.extensions.repeatOnViewLifecycle
import com.stas1270.githubapi.ui.base.extensions.showErrorToast
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


class RepoListFragment : Fragment() {

    @Inject
    lateinit var viewModel: RepoListViewModel

    private var _binding: FragmentRepoListBinding? = null
    private val binding get() = _binding!!

    private val adapter = RepoListAdapter {
        val action = RepoListFragmentDirections.actionRepoListFragmentToRepoDetailsFragment(it.id)
        findNavController().navigate(action)
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as GitHubApp).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentRepoListBinding.inflate(inflater, container, false)
            binding.repoList.adapter = adapter
            initialSearch()
            binding.btnSearch.setOnClickListener {
                hideKeyboard()
                binding.searchRepos.text.toString().takeIf { it.isNotEmpty() }?.let {
                    viewModel.search(it)
                }
            }
        }
        return binding.root
    }

    private fun hideKeyboard() {
        activity?.hideSoftKeyboard()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repeatOnViewLifecycle {
            viewModel.viewState.collectLatest {
                adapter.update(it.list)
            }
        }
        repeatOnViewLifecycle {
            viewModel.error.collectLatest {
                if (it) showErrorToast()
            }
        }
        repeatOnViewLifecycle {
            viewModel.viewStateIsLoading.collectLatest {
                binding.progressBar.isVisible = it
            }
        }
    }

    private fun initialSearch() {
        viewModel.search(binding.searchRepos.text.toString())
    }
}
