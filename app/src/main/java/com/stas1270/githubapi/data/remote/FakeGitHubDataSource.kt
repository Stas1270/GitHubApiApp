package com.stas1270.githubapi.data.remote

import com.stas1270.githubapi.data.local.model.ResponseData
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel
import com.stas1270.githubapi.ui.utils.FAKE_MODEL_COUNT

class FakeGitHubDataSource : GitHubDataSource {
    override suspend fun getRepos(search: String): ResponseData<List<RepoModel>> {
        return ResponseData.Success(generateRepoList(FAKE_MODEL_COUNT))
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

fun generateRepoList(count: Int): List<RepoModel> {
    return (0..count)
//        .asSequence()
        .map {
            RepoModel(
                id = it,
                name = "fake name $it",
                fullName = "fullName $it",
                url = "url.bad $it",
                language = "Kotlin $it",
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