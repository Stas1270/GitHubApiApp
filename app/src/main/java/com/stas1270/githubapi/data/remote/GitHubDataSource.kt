package com.stas1270.githubapi.data.remote

import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel

interface GitHubDataSource {
    suspend fun getRepos(search: String): ResponseData<List<RepoModel>>
    suspend fun getRepositoryDetails(id: Int): ResponseData<RepoDetailedModel?>
}
