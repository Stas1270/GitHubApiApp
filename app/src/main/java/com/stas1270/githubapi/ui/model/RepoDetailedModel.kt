package com.stas1270.githubapi.ui.model

data class RepoDetailedModel(
    val id: Int,
    val name: String,
    val createdAt: String,
    val description: String,
    val htmlUrl: String,
    val stargazersCount: Int,
    val updatedAt: String,
    val ownerAvatarUrl: String,
    val ownerLogin: String,
)