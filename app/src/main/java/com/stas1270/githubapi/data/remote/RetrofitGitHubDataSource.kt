package com.stas1270.githubapi.data.remote

import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.data.remote.model.RepoDetailsResult
import com.stas1270.githubapi.data.remote.model.RepoItem
import com.stas1270.githubapi.data.remote.model.ReposResponse
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel
import com.stas1270.githubapi.ui.utils.REQUEST_COUNT_REPOS
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface GitHubApi {

    @GET("search/repositories")
    suspend fun getRepos(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
    ): ReposResponse

    @GET("repositories/{repo_id}")
    suspend fun getRepositoryDetails(
        @Path("repo_id") id: Int,
    ): RepoDetailsResult

}

class RetrofitGitHubDataSource(private val api: GitHubApi) : GitHubDataSource {

    override suspend fun getRepos(search: String): ResponseData<List<RepoModel>> {
        return try {
            ResponseData.Success(
                api.getRepos(search, REQUEST_COUNT_REPOS)
                    .items.map {
                        it.toRepoModel()
                    }
            )
        } catch (exception: Exception) {
            when (exception) {
                is SocketTimeoutException,
                is UnknownHostException -> ResponseData.Error(exception)
                else -> throw exception
            }
        }
    }

    override suspend fun getRepositoryDetails(id: Int): ResponseData<RepoDetailedModel?> {
        return try {
            ResponseData.Success(api.getRepositoryDetails(id).toRepoDetailedModel())
        } catch (exception: Exception) {
            when (exception) {
                is SocketTimeoutException,
                is UnknownHostException -> ResponseData.Error(exception)
                else -> throw exception
            }
        }
    }
}

fun RepoItem.toRepoModel(): RepoModel {
    return RepoModel(
        id = id,
        name = name,
        fullName = fullName,
        url = url,
        language = language,
        ownerLogin = owner.login,
        ownerUrl = owner.url,
        ownerAvatarUrl = owner.avatarUrl
    )
}

fun RepoDetailsResult.toRepoDetailedModel(): RepoDetailedModel {
    return RepoDetailedModel(
        id = id,
        name = name,
        createdAt = createdAt.orEmpty(),
        description = description.orEmpty(),
        htmlUrl = htmlUrl.orEmpty(),
        stargazersCount = stargazersCount ?: 0,
        updatedAt = updatedAt.orEmpty(),
        ownerAvatarUrl = ownerDetails?.avatarUrl.orEmpty(),
        ownerLogin = ownerDetails?.login.orEmpty()
    )
}