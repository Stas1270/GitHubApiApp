package com.stas1270.githubapi.di

import android.content.Context
import com.stas1270.githubapi.data.local.FakeLocalDataSource
import com.stas1270.githubapi.data.local.LocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class FakeLocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(context: Context): LocalDataSource {
        return FakeLocalDataSource()
    }
}