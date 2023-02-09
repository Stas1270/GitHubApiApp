package com.stas1270.githubapi.data.local

import com.stas1270.githubapi.data.remote.generateRepoList
import com.stas1270.githubapi.data.remote.getFakeRepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel
import com.stas1270.githubapi.ui.utils.FAKE_MODEL_COUNT
import javax.inject.Inject

class FakeLocalDataSource @Inject constructor() : LocalDataSource {

    override suspend fun getRepos(search: String): List<RepoModel> {
        return generateRepoList(search, FAKE_MODEL_COUNT)
    }

    override suspend fun insertRepos(search: String, list: List<RepoModel>) {
        //no op
    }

    override suspend fun getRepositoryDetails(id: Int): RepoDetailedModel? {
        return getFakeRepoDetailedModel(id)
    }

    override suspend fun insertRepositoryDetails(detailedModel: RepoDetailedModel) {
        //no op
    }
}