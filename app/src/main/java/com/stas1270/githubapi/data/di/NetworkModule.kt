package com.stas1270.githubapi.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.stas1270.githubapi.data.remote.BASE_URL
import com.stas1270.githubapi.data.remote.GitHubApi
import com.stas1270.githubapi.data.remote.GitHubDataSource
import com.stas1270.githubapi.data.remote.RetrofitGitHubDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
open class NetworkModule {

    @Provides
    open fun provideMoshiConverterFactory(): Converter.Factory =
        MoshiConverterFactory.create(
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        )

    @Provides
    fun provideHttpLoggingInterceptor(): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()

    @Provides
    fun provideRetrofit(
        factory: Converter.Factory,
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(factory)
            .client(client)
            .build()

    @Provides
    fun provideGitHubApi(retrofit: Retrofit): GitHubApi =
        retrofit.create(GitHubApi::class.java)

    @Provides
    @Singleton
    open fun provideRetrofitGitHubDataSource(api: GitHubApi): GitHubDataSource =
        RetrofitGitHubDataSource(api)
}