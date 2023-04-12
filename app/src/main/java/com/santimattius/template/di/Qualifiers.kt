package com.santimattius.template.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InMemoryLocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RoomLocalDataSource
