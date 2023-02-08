package com.stas1270.githubapi.data.reposiory

import com.stas1270.githubapi.data.di.OpenClassOnDebug
import com.stas1270.githubapi.data.local.LocalDataSource
import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.data.local.model.suspendOnSuccess
import com.stas1270.githubapi.data.remote.GitHubDataSource
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@OpenClassOnDebug
class ReposRepositoryImpl @Inject constructor(
    val remote: GitHubDataSource,
    val local: LocalDataSource,
) : ReposRepository {

    override suspend fun getRepos(searchQuery: String): Flow<ResponseData<List<RepoModel>>> {
        return flow {
            val localResult = local.getRepos(searchQuery)
            if (localResult.isEmpty()) {
                remote.getRepos(searchQuery)
                    .suspendOnSuccess {
                        local.insertRepos(searchQuery, data)
                        emit(this)
                    }
            } else {
                emit(ResponseData.Success(localResult))
            }
        }
    }

    override suspend fun getRepositoryDetails(id: Int): Flow<ResponseData<RepoDetailedModel?>> {
        return flow {
            val localResult = local.getRepositoryDetails(id)
            if (localResult == null) {
                remote.getRepositoryDetails(id)
                    .suspendOnSuccess {
                        data?.let {
                            local.insertRepositoryDetails(data)
                            emit(this)
                        }
                    }
            } else {
                emit(ResponseData.Success(localResult))
            }
        }
    }
}