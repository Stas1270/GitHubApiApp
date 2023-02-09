package com.stas1270.githubapi.di.mock

import android.content.Context
import com.stas1270.githubapi.GitHubApp
import com.stas1270.githubapi.data.di.AppModule
import com.stas1270.githubapi.data.reposiory.ReposRepository
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestMockComponentsRule(val context: Context) : TestRule {

    val testComponent: TestMockAppComponent

    init {
        val instrument = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
        val application = instrument.targetContext.applicationContext as GitHubApp
        testComponent = DaggerTestMockAppComponent.builder()
                .appModule(AppModule(application))
                .build()
    }

    val mockDataManager: ReposRepository
        get() = testComponent.repoRepository()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                val instrument = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
                val application = instrument.targetContext.applicationContext as GitHubApp
                application.appComponent = testComponent
                base.evaluate()
            }
        }
    }
}