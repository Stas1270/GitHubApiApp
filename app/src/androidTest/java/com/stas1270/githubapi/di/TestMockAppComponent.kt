package com.stas1270.githubapi.di

import com.stas1270.githubapi.data.di.AppModule
import com.stas1270.githubapi.data.di.ApplicationComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FakeNetworkModule::class,
        FakeLocalDataModule::class,
        MockRepositoryModule::class,
        AppModule::class]
)
interface TestMockAppComponent : ApplicationComponent {

}
