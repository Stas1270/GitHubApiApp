package com.stas1270.githubapi.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QueryRepoEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "query_id") val queryId: Int,
    @ColumnInfo(name = "repo_id") val repoId: Int
) {
    constructor(queryId: Int, repoId: Int) : this(0, queryId, repoId)
}
