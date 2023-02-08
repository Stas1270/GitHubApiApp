package com.stas1270.githubapi

import com.stas1270.githubapi.data.di.AppModule
import com.stas1270.githubapi.data.di.ApplicationComponent
import com.stas1270.githubapi.data.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class GitHubApp : DaggerApplication() {

    //    val appComponent: TestAppComponent by lazy {
//        initializeComponent()
//    }
//
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return initializeComponent()
    }

    open fun initializeComponent(): ApplicationComponent {
        val component: ApplicationComponent =  DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()
        component.inject(this)
        return component
    }
}