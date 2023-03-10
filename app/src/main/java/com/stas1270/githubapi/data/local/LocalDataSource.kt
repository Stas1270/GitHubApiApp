package com.stas1270.githubapi.data.local

import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel

interface LocalDataSource {
    suspend fun getRepos(search: String): List<RepoModel>
    suspend fun insertRepos(search: String, list: List<RepoModel>)
    suspend fun getRepositoryDetails(id: Int): RepoDetailedModel?
    suspend fun insertRepositoryDetails(detailedModel: RepoDetailedModel)
}
