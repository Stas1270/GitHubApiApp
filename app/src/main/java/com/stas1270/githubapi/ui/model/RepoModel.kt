package com.stas1270.githubapi.ui.model

data class RepoModel(
    val id: Int,
    val name: String,
    val fullName: String,
    val url: String,
    val language: String? = null,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
    val ownerUrl: String,
)