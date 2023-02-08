package com.stas1270.githubapi.data.di

import com.stas1270.githubapi.GitHubApp
import com.stas1270.githubapi.ui.repo_details.RepoDetailsFragment
import com.stas1270.githubapi.ui.repolist.RepoListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
//        AndroidInjectionModule::class,
//        ActivityBuildersModule::class,
        NetworkModule::class,
        LocalDataModule::class,
        RepositoryModule::class,
        AppModule::class]
)
interface ApplicationComponent {

    fun inject(fragment: RepoListFragment)
    fun inject(fragment: RepoDetailsFragment)
    fun inject(app: GitHubApp)
}