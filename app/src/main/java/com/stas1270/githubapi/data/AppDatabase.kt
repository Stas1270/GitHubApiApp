package com.stas1270.githubapi.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stas1270.githubapi.data.local.dao.QueryRepoDao
import com.stas1270.githubapi.data.local.dao.RepoDetailedModelDao
import com.stas1270.githubapi.data.local.dao.RepoModelDao
import com.stas1270.githubapi.data.local.entity.QueryEntity
import com.stas1270.githubapi.data.local.entity.QueryRepoEntity
import com.stas1270.githubapi.data.local.entity.RepoDetailedModelEntity
import com.stas1270.githubapi.data.local.entity.RepoModelEntity


@Database(
    entities = [
        RepoModelEntity::class,
        QueryRepoEntity::class,
        RepoDetailedModelEntity::class,
        QueryEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoModelDao(): RepoModelDao
    abstract fun queryRepoDao(): QueryRepoDao
    abstract fun repoDetailedModelDao(): RepoDetailedModelDao
}