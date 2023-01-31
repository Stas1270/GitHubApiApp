package com.stas1270.githubapi.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepoModelEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "language") val language: String? = null,
    @ColumnInfo(name = "owner_login") val ownerLogin: String,
    @ColumnInfo(name = "owner_avatar_url") val ownerAvatarUrl: String,
    @ColumnInfo(name = "owner_url") val ownerUrl: String,
)
