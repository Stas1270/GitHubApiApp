package com.stas1270.githubapi.data.reposiory

import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel
import kotlinx.coroutines.flow.Flow

interface ReposRepository {
    suspend fun getRepos(search: String): Flow<ResponseData<List<RepoModel>>>
    suspend fun getRepositoryDetails(id: Int): ResponseData<RepoDetailedModel?>
}