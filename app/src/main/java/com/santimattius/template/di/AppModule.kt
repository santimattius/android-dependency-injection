package com.santimattius.template.di

import com.santimattius.template.data.client.database.AppDataBase
import com.santimattius.template.data.client.network.RetrofitServiceCreator
import com.santimattius.template.data.datasources.implementation.MovieDataSource
import com.santimattius.template.data.datasources.implementation.RoomDataSource
import com.santimattius.template.data.repositories.TMDbRepository
import com.santimattius.template.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        HomeViewModel()
    }

    single {
        RetrofitServiceCreator()
    }

    single {
        AppDataBase.get(androidApplication())
    }

    factory {
        RoomDataSource()
    }

    factory {
        MovieDataSource()
    }

    factory {
        TMDbRepository()
    }
}