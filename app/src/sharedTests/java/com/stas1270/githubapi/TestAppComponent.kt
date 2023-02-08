package com.stas1270.githubapi

import com.stas1270.githubapi.data.di.AppModule
import com.stas1270.githubapi.data.di.ApplicationComponent
import com.stas1270.githubapi.data.di.RepositoryModule
import com.stas1270.githubapi.di.TestActivityBuildersModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        TestActivityBuildersModule::class,
        com.stas1270.githubapi.di.FakeNetworkModule::class,
        com.stas1270.githubapi.di.FakeLocalDataModule::class,
        RepositoryModule::class,
        AppModule::class]
)
interface TestAppComponent: ApplicationComponent {

    fun inject(app: TestApplication)
}
