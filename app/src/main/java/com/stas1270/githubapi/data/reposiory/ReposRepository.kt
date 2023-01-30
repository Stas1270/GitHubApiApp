package com.stas1270.githubapi.data.reposiory

import com.stas1270.githubapi.data.local.model.RepositoryData
import com.stas1270.githubapi.ui.model.RepoModel

interface ReposRepository {
    suspend fun getRepos(search: String): RepositoryData<List<RepoModel>>
}