package com.stas1270.githubapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stas1270.githubapi.data.local.entity.RepoModelEntity

@Dao
interface RepoModelDao {

    @Query(
        "SELECT RepoModelEntity.* FROM RepoModelEntity, QueryEntity, QueryRepoEntity " +
                "WHERE RepoModelEntity.uid = QueryRepoEntity.repo_id " +
                "AND QueryEntity.queryRequest =:query " +
                "AND QueryEntity.uid = QueryRepoEntity.query_id"
    )
    suspend fun loadAllByQuery(query: String): List<RepoModelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoModelEntity>)
}