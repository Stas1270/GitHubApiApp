package com.stas1270.githubapi.di

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.stas1270.githubapi.TestApplication

class GithubTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}