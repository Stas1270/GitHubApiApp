package com.stas1270.githubapi.data.di

import com.stas1270.githubapi.GitHubApp
import com.stas1270.githubapi.data.reposiory.ReposRepository
import com.stas1270.githubapi.ui.repo_details.RepoDetailsFragment
import com.stas1270.githubapi.ui.repolist.RepoListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        LocalDataModule::class,
        RepositoryModule::class,
        AppModule::class]
)
interface ApplicationComponent {

    fun inject(fragment: RepoListFragment)
    fun inject(fragment: RepoDetailsFragment)
    fun inject(app: GitHubApp)

    fun repoRepository(): ReposRepository
}