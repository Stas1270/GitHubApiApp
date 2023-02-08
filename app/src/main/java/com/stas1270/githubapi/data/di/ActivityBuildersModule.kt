package com.stas1270.githubapi.data.di

import com.stas1270.githubapi.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuildersModule {

//    @ActivityScope
    @ContributesAndroidInjector(modules = [/*MainModule::class,*/ MainFragmentsBuilderModule::class])
    fun contributeMainActivity(): MainActivity

}