package com.stas1270.githubapi.data.di

import android.content.Context
import androidx.room.Room
import com.stas1270.githubapi.data.AppDatabase
import com.stas1270.githubapi.data.local.LocalDataSource
import com.stas1270.githubapi.data.local.LocalGitHubDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class])
open class LocalDataModule {

    companion object {
        private const val DATABASE_NAME = "AppDatabase"
    }

    @Provides
    @Singleton
     fun provideLocalDataSource(context: Context): LocalDataSource {
        val db = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()
        return LocalGitHubDataSource(
            db.queryRepoDao(),
            db.repoModelDao(),
            db.repoDetailedModelDao()
        )
    }

//    @Singleton
//    @Provides
//    open fun provideAppDatabase(context: Context): AppDatabase =
//
}
