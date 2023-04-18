package com.santimattius.template.di

import android.app.Application
import com.santimattius.template.BuildConfig
import com.santimattius.template.data.client.database.AppDataBase
import com.santimattius.template.data.client.network.RetrofitServiceCreator
import com.santimattius.template.data.client.network.TheMovieDBService
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class AppModule {

    @Single
    fun provideAppDatabase(application: Application): AppDataBase = AppDataBase.get(application)

    @Single
    fun provideMovieDBService(): TheMovieDBService =
        RetrofitServiceCreator.create(BuildConfig.API_KEY)

}