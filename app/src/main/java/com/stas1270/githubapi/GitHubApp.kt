package com.stas1270.githubapi

import android.app.Application
import com.stas1270.githubapi.data.di.AppModule
import com.stas1270.githubapi.data.di.ApplicationComponent
import com.stas1270.githubapi.data.di.DaggerApplicationComponent

open class GitHubApp : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initializeComponent()
    }

    open fun initializeComponent(): ApplicationComponent {
        val component: ApplicationComponent = DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()
        component.inject(this)
        return component
    }
}