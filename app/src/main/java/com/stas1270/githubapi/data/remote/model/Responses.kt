package com.stas1270.githubapi.data.remote.model

import com.squareup.moshi.Json

data class ReposResponse(
    @Json(name = "incomplete_results") val incompleteResults: Boolean,
    @Json(name = "items") val items: List<RepoItem>,
    @Json(name = "total_count") val totalCount: Int
)

data class RepoItem(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "owner") val owner: Owner,
    @Json(name = "html_url") val url: String,
    @Json(name = "language") val language: String?,
)

data class Owner(
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "html_url") val url: String,
)

data class RepoDetailsResult(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "created_at") val createdAt: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "html_url") val htmlUrl: String?,
    @Json(name = "owner") val ownerDetails: OwnerDetails?,
    @Json(name = "stargazers_count") val stargazersCount: Int?,
    @Json(name = "updated_at") val updatedAt: String?
)

data class OwnerDetails(
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val avatarUrl: String,
)