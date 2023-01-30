package com.stas1270.githubapi.data.reposiory

import com.stas1270.githubapi.data.GitHubDataSource
import com.stas1270.githubapi.data.di.qualifiers.RemoteDataSource
import com.stas1270.githubapi.ui.model.RepoModel
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor(
    @RemoteDataSource val repo: GitHubDataSource
) : ReposRepository {

    override suspend fun getRepos(search: String): List<RepoModel> {
        return repo.getRepos(search)
    }
}