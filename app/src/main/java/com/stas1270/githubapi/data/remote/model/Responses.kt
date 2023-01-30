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
    @Json(name = "owner") val owner: Owner,
)

data class Owner(
    @Json(name = "login") val author: String,
)