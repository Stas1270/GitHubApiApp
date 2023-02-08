package com.stas1270.githubapi.data.di

import com.stas1270.githubapi.GitHubApp
import com.stas1270.githubapi.ui.repo_details.RepoDetailsFragment
import com.stas1270.githubapi.ui.repolist.RepoListFragment
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBuildersModule::class,
        NetworkModule::class,
        LocalDataModule::class,
        RepositoryModule::class,
        AppModule::class]
)
interface ApplicationComponent: AndroidInjector<DaggerApplication> {

    fun inject(fragment: RepoListFragment)
    fun inject(fragment: RepoDetailsFragment)
    fun inject(app: GitHubApp)
}