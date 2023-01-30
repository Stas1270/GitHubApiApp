package com.stas1270.githubapi.data.local.model

class RepositoryData<T>(
    val data: T,
    val status: Status
)

sealed class Status
object Success : Status()
object Error : Status()
