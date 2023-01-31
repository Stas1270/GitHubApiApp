package com.stas1270.githubapi.data.reposiory

import com.stas1270.githubapi.data.GitHubDataSource
import com.stas1270.githubapi.data.di.qualifiers.RemoteDataSource
import com.stas1270.githubapi.data.local.model.Error
import com.stas1270.githubapi.data.local.model.RepositoryData
import com.stas1270.githubapi.data.local.model.Success
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor(
    @RemoteDataSource val repo: GitHubDataSource
) : ReposRepository {

    override suspend fun getRepos(search: String): RepositoryData<List<RepoModel>> {
        return try {
            RepositoryData(repo.getRepos(search), Success)
        } catch (ex: Exception) {
            when (ex) {
                is SocketTimeoutException,
                is UnknownHostException -> RepositoryData(emptyList(), Error)
                else -> throw ex
            }
        }
    }

    override suspend fun getRepositoryDetails(id: Int): RepositoryData<RepoDetailedModel?> {
        return try {
            RepositoryData(repo.getRepositoryDetails(id), Success)
        } catch (ex: Exception) {
            when (ex) {
                is SocketTimeoutException,
                is UnknownHostException -> RepositoryData(null, Error)
                else -> throw ex
            }
        }
    }
}