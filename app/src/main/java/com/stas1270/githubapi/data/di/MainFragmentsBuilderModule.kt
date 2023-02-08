package com.stas1270.githubapi.data.di

import com.stas1270.githubapi.ui.repo_details.RepoDetailsFragment
import com.stas1270.githubapi.ui.repolist.RepoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface MainFragmentsBuilderModule {

    @ContributesAndroidInjector
    fun contributeMainFragment(): RepoListFragment

    @ContributesAndroidInjector
    fun contributeMainFragment1(): RepoDetailsFragment

}