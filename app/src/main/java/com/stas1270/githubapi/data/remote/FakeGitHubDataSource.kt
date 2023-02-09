package com.stas1270.githubapi.data.remote

import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel
import com.stas1270.githubapi.ui.utils.FAKE_MODEL_COUNT

class FakeGitHubDataSource : GitHubDataSource {
    override suspend fun getRepos(search: String): ResponseData<List<RepoModel>> {
        return ResponseData.Success(generateRepoList(search, FAKE_MODEL_COUNT))
    }

    override suspend fun getRepositoryDetails(id: Int): ResponseData<RepoDetailedModel?> {
        return ResponseData.Success(getFakeRepoDetailedModel(id))
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

fun generateRepoList(search: String, count: Int): List<RepoModel> {
    val searchQuery = search.lowercase()
    return (0 until count)
        .map {
            RepoModel(
                id = it,
                name = "fake name $it",
                fullName = "$searchQuery fullName $it",
                url = "$searchQuery url.bad $it",
                language = "$searchQuery $it",
                ownerUrl = "owner.url.bad",
                ownerAvatarUrl = "owner.avatar.url.bad",
                ownerLogin = "owner login"
            )
        }.toList()
}

fun getFakeRepoDetailedModel(id: Int): RepoDetailedModel {
    return RepoDetailedModel(
        id = id,
        name = "fake name $id",
        createdAt = "createdAt",
        description = "description",
        htmlUrl = "htmlUrl",
        stargazersCount = -1,
        updatedAt = "updatedAt",
        ownerAvatarUrl = "owneravatarUrl",
        ownerLogin = "ownerDetails.login"
    )
}