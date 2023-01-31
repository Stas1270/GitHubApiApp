package com.stas1270.githubapi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.stas1270.githubapi.data.local.entity.QueryEntity
import com.stas1270.githubapi.data.local.entity.QueryRepoEntity

@Dao
interface QueryRepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchRequest(repos: QueryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQueryRepoEntities(entities: List<QueryRepoEntity>)
}