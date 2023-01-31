package com.stas1270.githubapi.data.local

import com.stas1270.githubapi.data.remote.getFakeRepoDetailedModel
import com.stas1270.githubapi.data.remote.getFakeRepoModel
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel
import javax.inject.Inject

class FakeLocalDataSource @Inject constructor() : LocalDataSource {

    override suspend fun getRepos(search: String): List<RepoModel> {
        return listOf(getFakeRepoModel())
    }

    override suspend fun insertRepos(search: String, list: List<RepoModel>) {
        //no op
    }

    override suspend fun getRepositoryDetails(id: Int): RepoDetailedModel? {
        return getFakeRepoDetailedModel()
    }

    override suspend fun insertRepositoryDetails(detailedModel: RepoDetailedModel) {
        //no op
    }
}