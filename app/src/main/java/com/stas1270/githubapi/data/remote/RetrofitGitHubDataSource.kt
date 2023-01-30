package com.stas1270.githubapi.data.remote

import com.stas1270.githubapi.data.GitHubDataSource
import com.stas1270.githubapi.data.remote.model.RepoItem
import com.stas1270.githubapi.data.remote.model.ReposResponse
import com.stas1270.githubapi.ui.model.RepoModel
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories")
    suspend fun getRepos(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
    ): ReposResponse
}

class RetrofitGitHubDataSource(private val api: GitHubApi) : GitHubDataSource {

    override suspend fun getRepos(search: String): List<RepoModel> {
        return api.getRepos(search, 100).items
            .map {
                it.toRepoModel()
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