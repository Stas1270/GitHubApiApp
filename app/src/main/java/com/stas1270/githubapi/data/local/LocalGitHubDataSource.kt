package com.stas1270.githubapi.data.local

import com.stas1270.githubapi.data.local.dao.QueryRepoDao
import com.stas1270.githubapi.data.local.dao.RepoDetailedModelDao
import com.stas1270.githubapi.data.local.dao.RepoModelDao
import com.stas1270.githubapi.data.local.entity.QueryEntity
import com.stas1270.githubapi.data.local.entity.QueryRepoEntity
import com.stas1270.githubapi.data.local.entity.RepoDetailedModelEntity
import com.stas1270.githubapi.data.local.entity.RepoModelEntity
import com.stas1270.githubapi.ui.model.RepoDetailedModel
import com.stas1270.githubapi.ui.model.RepoModel
import javax.inject.Inject

class LocalGitHubDataSource @Inject constructor(
    private val queryRepoDao: QueryRepoDao,
    private val repoModelDao: RepoModelDao,
    private val repoDetailedModelDao: RepoDetailedModelDao,
) : LocalDataSource {

    override suspend fun getRepos(search: String): List<RepoModel> {
        return repoModelDao.loadAllByQuery(search)
            .map {
                it.toRepoModel()
            }
    }

    override suspend fun insertRepos(search: String, list: List<RepoModel>) {
        repoModelDao.insertAll(list.map { model ->
            model.toRepoModelEntity()
        })
        val row = queryRepoDao.insertSearchRequest(QueryEntity(name = search))
        queryRepoDao.insertQueryRepoEntities(
            list.map {
                QueryRepoEntity(row.toInt(), it.id)
            })
    }

    override suspend fun getRepositoryDetails(id: Int): RepoDetailedModel? {
        return repoDetailedModelDao.getRepositoryDetails(id)?.toRepoDetailedModel()
    }

    override suspend fun insertRepositoryDetails(detailedModel: RepoDetailedModel) {
        return repoDetailedModelDao.insert(detailedModel.toRepoDetailedModelEntity())
    }
}

fun RepoDetailedModel.toRepoDetailedModelEntity(): RepoDetailedModelEntity {
    return RepoDetailedModelEntity(
        id,
        name,
        description,
        stargazersCount,
        createdAt,
        updatedAt,
        htmlUrl,
        ownerLogin,
        ownerAvatarUrl,
    )
}

fun RepoDetailedModelEntity.toRepoDetailedModel(): RepoDetailedModel {
    return RepoDetailedModel(
        uid,
        name,
        createdAt,
        description,
        htmlUrl,
        stargazersCount,
        updatedAt,
        ownerAvatarUrl,
        ownerLogin
    )
}

fun RepoModelEntity.toRepoModel(): RepoModel {
    return RepoModel(
        uid,
        name,
        fullName,
        url,
        language,
        ownerLogin,
        ownerAvatarUrl,
        ownerUrl
    )
}

fun RepoModel.toRepoModelEntity(): RepoModelEntity {
    return RepoModelEntity(
        id,
        name,
        fullName,
        url,
        language,
        ownerLogin,
        ownerAvatarUrl,
        ownerUrl
    )
}