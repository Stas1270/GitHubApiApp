package com.stas1270.githubapi.di

import com.stas1270.githubapi.EmptyMainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface TestActivityBuildersModule {

//    @ActivityScope
    @ContributesAndroidInjector(modules = [/*MainModule::class,*/ MainFragmentsBuilderModule::class])
    fun contributeMainActivity(): EmptyMainActivity

}