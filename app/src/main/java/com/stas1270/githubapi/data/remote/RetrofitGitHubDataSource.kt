package com.stas1270.githubapi.data.remote

import com.stas1270.githubapi.data.GitHubDataSource
import com.stas1270.githubapi.data.remote.model.RepoDetailsResult
import com.stas1270.githubapi.data.remote.model.RepoItem
import com.stas1270.githubapi.data.remote.model.ReposResponse
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    override suspend fun getRepos(search: String): List<RepoModel> {
        return api.getRepos(search, 100).items
            .map {
                it.toRepoModel()
            }
    }

    override suspend fun getRepositoryDetails(id: Int): RepoDetailedModel {
        return api.getRepositoryDetails(id).toRepoDetailedModel()
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
        createdAt = createdAt,
        description = description,
        htmlUrl = htmlUrl,
        stargazersCount = stargazersCount,
        updatedAt = updatedAt,
        ownerAvatarUrl = ownerDetails.avatarUrl,
        ownerLogin = ownerDetails.login
    )
}