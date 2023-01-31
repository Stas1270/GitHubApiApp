package com.stas1270.githubapi.data.remote

import com.skydoves.sandwich.ApiResponse
import com.stas1270.githubapi.data.GitHubDataSource
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel
import retrofit2.Response

class FakeGitHubDataSource : GitHubDataSource {

    override suspend fun getRepos(search: String): ApiResponse<List<RepoModel>> {
        return ApiResponse.Success(Response.success(listOf(getFakeRepoModel())))
    }

    override suspend fun getRepositoryDetails(id: Int): RepoDetailedModel {
        return getFakeRepoDetailedModel()
    }
}

fun getFakeRepoModel(): RepoModel {
    return RepoModel(
        id = 123321,
        name = "fake name",
        fullName = "fullName",
        url = "url.bad",
        language = "Kotlin",
        ownerUrl = "owner.url.bad",
        ownerAvatarUrl = "owner.avatar.url.bad",
        ownerLogin = "owner login"
    )
}


fun getFakeRepoDetailedModel(): RepoDetailedModel {
    return RepoDetailedModel(
        id = 123321,
        name = "fake name",
        createdAt = "createdAt",
        description = "description",
        htmlUrl = "htmlUrl",
        stargazersCount = -1,
        updatedAt = "updatedAt",
        ownerAvatarUrl = "owneravatarUrl",
        ownerLogin = "ownerDetails.login"
    )
}