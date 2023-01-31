package com.stas1270.githubapi.data.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteDataSourceQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalDataSourceQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FakeLocalDataSourceQualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FakeRemoteDataSourceQualifier
