package com.stas1270.githubapi

import android.app.Application
import com.stas1270.githubapi.data.di.ApplicationComponent
import com.stas1270.githubapi.data.di.DaggerApplicationComponent

class GitHubApp : Application() {

    val appComponent: ApplicationComponent by lazy {
        initializeComponent()
    }

    private fun initializeComponent(): ApplicationComponent {
        return  DaggerApplicationComponent.create()
    }
}