package com.stas1270.githubapi.data.di

import com.stas1270.githubapi.data.local.LocalDataSource
import com.stas1270.githubapi.data.remote.GitHubDataSource
import com.stas1270.githubapi.data.reposiory.ReposRepository
import com.stas1270.githubapi.data.reposiory.ReposRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class RepositoryModule {

    @Singleton
    @Provides
    open fun provideReposRepository(
        localDataSource: LocalDataSource,
        gitHubDataSource: GitHubDataSource
    ): ReposRepository = ReposRepositoryImpl(gitHubDataSource, localDataSource)

}