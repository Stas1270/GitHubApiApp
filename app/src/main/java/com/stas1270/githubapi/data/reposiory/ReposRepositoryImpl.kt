package com.stas1270.githubapi.data.reposiory

import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.stas1270.githubapi.data.GitHubDataSource
import com.stas1270.githubapi.data.di.qualifiers.LocalDataSourceQualifier
import com.stas1270.githubapi.data.di.qualifiers.RemoteDataSourceQualifier
import com.stas1270.githubapi.data.local.LocalDataSource
import com.stas1270.githubapi.data.local.model.Error
import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.data.local.model.Success
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ReposRepositoryImpl @Inject constructor(
    @RemoteDataSourceQualifier val remote: GitHubDataSource,
    @LocalDataSourceQualifier val local: LocalDataSource,
) : ReposRepository {

    override suspend fun getRepos(searchQuery: String): Flow<ResponseData<List<RepoModel>>> {
        return flow {
            val localResult = local.getRepos(searchQuery)
            if (localResult.isEmpty()) {
                remote.getRepos(searchQuery)
                    .suspendOnSuccess {
                        local.insertRepos(searchQuery, data)
                        emit(ResponseData(data, Success))
                    }
                    .suspendOnException {
                        when (exception) {
                            is SocketTimeoutException,
                            is UnknownHostException -> emit(ResponseData(emptyList(), Error))
                            else -> throw exception
                        }
                    }
            } else {
                emit(ResponseData(localResult, Success))
            }
        }
    }

    override suspend fun getRepositoryDetails(id: Int): Flow<ResponseData<RepoDetailedModel?>> {
        return flow {
            val localResult = local.getRepositoryDetails(id)
            if (localResult == null) {
                remote.getRepositoryDetails(id)
                    .suspendOnSuccess {
                        local.insertRepositoryDetails(data)
                        emit(ResponseData(data, Success))
                    }
                    .suspendOnException {
                        when (exception) {
                            is SocketTimeoutException,
                            is UnknownHostException -> emit(ResponseData(null, Error))
                            else -> throw exception
                        }
                    }
            } else {
                emit(ResponseData(localResult, Success))
            }
        }
    }
}