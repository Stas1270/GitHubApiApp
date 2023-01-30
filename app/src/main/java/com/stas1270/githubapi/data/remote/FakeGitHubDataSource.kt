package com.stas1270.githubapi.data.remote

import com.stas1270.githubapi.data.GitHubDataSource
import com.stas1270.githubapi.ui.model.RepoModel

class FakeGitHubDataSource : GitHubDataSource {

    override suspend fun getRepos(search: String): List<RepoModel> {
        return listOf(RepoModel(text = "Not yet implemented"))
    }
}