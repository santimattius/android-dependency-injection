package com.santimattius.template.data.datasources.implementation

import com.santimattius.template.data.client.network.RetrofitServiceCreator
import com.santimattius.template.data.client.network.TheMovieDBService
import com.santimattius.template.data.datasources.RemoteDataSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.santimattius.template.data.entities.MovieDto as TheMovieDbMovie

class MovieDataSource : RemoteDataSource, KoinComponent {

    private val serviceCreator: RetrofitServiceCreator by inject()

    @Suppress("TooGenericExceptionCaught")
    override suspend fun getPopularMovies(): Result<List<TheMovieDbMovie>> {
        return try {
            val response = serviceCreator.create<TheMovieDBService>()
                .getMoviePopular(version = DEFAULT_VERSION, page = SINGLE_PAGE)
            Result.success(response.results)
        } catch (ex: Throwable) {
            Result.failure(ex)
        }
    }

    companion object {
        private const val SINGLE_PAGE = 1
        const val DEFAULT_VERSION = 3
    }
}
