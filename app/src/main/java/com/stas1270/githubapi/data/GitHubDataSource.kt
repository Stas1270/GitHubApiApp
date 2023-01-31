package com.stas1270.githubapi.data

import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel

interface GitHubDataSource {
    suspend fun getRepos(search: String): List<RepoModel>
    suspend fun getRepositoryDetails(id: Int): RepoDetailedModel
}


