package com.stas1270.githubapi.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }
}