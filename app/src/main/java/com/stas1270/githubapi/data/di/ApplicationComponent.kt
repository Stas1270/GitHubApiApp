package com.stas1270.githubapi.data.di

import com.stas1270.githubapi.ui.repolist.RepoListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepositoryModule::class])
interface ApplicationComponent {

    fun inject(fragment: RepoListFragment)

}