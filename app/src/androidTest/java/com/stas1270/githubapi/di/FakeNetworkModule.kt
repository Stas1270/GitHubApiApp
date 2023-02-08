package com.stas1270.githubapi.di

import com.stas1270.githubapi.data.remote.FakeGitHubDataSource
import com.stas1270.githubapi.data.remote.GitHubDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FakeNetworkModule {

    @Singleton
    @Provides
    fun provideGitHubDataSource(): GitHubDataSource {
        return FakeGitHubDataSource()
    }
}