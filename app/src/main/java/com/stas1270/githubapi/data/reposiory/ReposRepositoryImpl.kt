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

    override suspend fun getRepos(search: String): Flow<ResponseData<List<RepoModel>>> {
        return flow {
            val localResult = local.getRepos(search)
            if (localResult.isEmpty()) {
                remote.getRepos(search)
                    .suspendOnSuccess {
                        local.insertRepos(search, data)
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

    override suspend fun getRepositoryDetails(id: Int): ResponseData<RepoDetailedModel?> {
        return try {
            ResponseData(remote.getRepositoryDetails(id), Success)
        } catch (ex: Exception) {
            when (ex) {
                is SocketTimeoutException,
                is UnknownHostException -> ResponseData(null, Error)
                else -> throw ex
            }
        }
    }
}