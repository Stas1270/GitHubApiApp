package com.stas1270.githubapi.data.di

import com.stas1270.githubapi.data.reposiory.ReposRepository
import com.stas1270.githubapi.data.reposiory.ReposRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindReposRepository(impl: ReposRepositoryImpl): ReposRepository
}