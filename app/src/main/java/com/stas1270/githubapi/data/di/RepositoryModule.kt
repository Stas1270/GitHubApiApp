package com.stas1270.githubapi.data.di

import android.content.Context
import androidx.room.Room
import com.stas1270.githubapi.data.AppDatabase
import com.stas1270.githubapi.data.di.qualifiers.FakeLocalDataSourceQualifier
import com.stas1270.githubapi.data.di.qualifiers.LocalDataSourceQualifier
import com.stas1270.githubapi.data.local.FakeLocalDataSource
import com.stas1270.githubapi.data.local.LocalDataSource
import com.stas1270.githubapi.data.local.LocalGitHubDataSource
import com.stas1270.githubapi.data.reposiory.ReposRepository
import com.stas1270.githubapi.data.reposiory.ReposRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsReposRepository(d: ReposRepositoryImpl): ReposRepository

    @Binds
    @FakeLocalDataSourceQualifier
    @Singleton
    abstract fun bindsFakeLocalDataSource(source: FakeLocalDataSource): LocalDataSource

    companion object {
        private const val DATABASE_NAME = "AppDatabase"

        @Provides
        @LocalDataSourceQualifier
        @Singleton
        fun provideLocalDataSource(db: AppDatabase): LocalDataSource {
            return LocalGitHubDataSource(
                db.queryRepoDao(),
                db.repoModelDao()
            )
        }

        @Singleton
        @Provides
        fun provideAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
    }
}