package com.santimattius.template.di

import android.app.Application
import android.content.Context
import com.santimattius.template.BuildConfig
import com.santimattius.template.data.client.database.AppDataBase
import com.santimattius.template.data.client.network.RetrofitServiceCreator
import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.datasources.LocalDataSource
import com.santimattius.template.data.datasources.RemoteDataSource
import com.santimattius.template.data.datasources.implementation.MovieDataSource
import com.santimattius.template.data.datasources.implementation.RoomDataSource
import com.santimattius.template.data.repositories.TMDbRepository
import com.santimattius.template.domain.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideMovieRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
    ): MovieRepository = TMDbRepository(
        remoteDataSource = remoteDataSource,
        localDataSource = localDataSource
    )

    @Provides
    fun provideLocalDataSource(appDataBase: AppDataBase): LocalDataSource {
        return RoomDataSource(dao = appDataBase.dao())
    }

    @Provides
    fun provideAppDatabase(application: Application): AppDataBase = AppDataBase.get(application)

    @Provides
    fun provideRemoteDataSource(service: TheMovieDBService): RemoteDataSource {
        return MovieDataSource(service = service)
    }

    @Provides
    fun provideMovieDBService(): TheMovieDBService =
        RetrofitServiceCreator.create(BuildConfig.API_KEY)


}