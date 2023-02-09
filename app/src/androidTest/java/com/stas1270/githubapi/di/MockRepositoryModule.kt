package com.stas1270.githubapi.di

import com.stas1270.githubapi.data.local.LocalDataSource
import com.stas1270.githubapi.data.remote.GitHubDataSource
import com.stas1270.githubapi.data.reposiory.ReposRepository
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module
open class MockRepositoryModule {

    @Singleton
    @Provides
    open fun provideReposRepository(
        localDataSource: LocalDataSource,
        gitHubDataSource: GitHubDataSource
    ): ReposRepository = mockk()
}