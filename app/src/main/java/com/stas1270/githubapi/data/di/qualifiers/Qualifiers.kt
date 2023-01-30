package com.stas1270.githubapi.data.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FakeRemoteDataSource
