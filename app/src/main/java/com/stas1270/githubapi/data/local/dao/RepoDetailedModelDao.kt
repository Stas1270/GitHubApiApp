package com.stas1270.githubapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stas1270.githubapi.data.local.entity.RepoDetailedModelEntity

@Dao
interface RepoDetailedModelDao {

    @Query("SELECT * FROM RepoDetailedModelEntity WHERE uid = :id")
    suspend fun getRepositoryDetails(id: Int): RepoDetailedModelEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: RepoDetailedModelEntity)
}