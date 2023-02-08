package com.stas1270.githubapi.di

import com.stas1270.githubapi.DaggerTestAppComponent
import com.stas1270.githubapi.GitHubApp
import com.stas1270.githubapi.data.di.AppModule
import com.stas1270.githubapi.data.di.ApplicationComponent

class TestApplication : GitHubApp() {

    override fun initializeComponent(): ApplicationComponent {
        val component: TestAppComponent = DaggerTestAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        return component
    }
}