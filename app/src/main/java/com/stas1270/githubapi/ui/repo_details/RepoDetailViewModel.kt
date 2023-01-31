package com.stas1270.githubapi.ui.repo_details

import com.stas1270.githubapi.data.local.model.Error
import com.stas1270.githubapi.data.local.model.Success
import com.stas1270.githubapi.data.reposiory.ReposRepository
import com.stas1270.githubapi.ui.base.BaseViewModel
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepoDetailViewModel @Inject constructor(
    private val reposRepository: ReposRepository
) : BaseViewModel() {

    private val _viewState = MutableStateFlow<RepoDetailedModel?>(null)
    val viewState
        get() = _viewState.asStateFlow().filterNotNull()

    private val _error = MutableStateFlow(false)
    val error
        get() = _error.asStateFlow()

    fun getRepoDetails(id: Int) {
        launch(Dispatchers.IO) {
            reposRepository.getRepositoryDetails(id)
                .collectLatest {
                    when (it.status) {
                        is Success -> _viewState.value = it.data
                        is Error -> _error.value = true
                    }
                }
        }
    }
}