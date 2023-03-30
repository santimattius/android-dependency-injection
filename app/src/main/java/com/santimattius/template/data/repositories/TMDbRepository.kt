package com.santimattius.template.data.repositories

import com.santimattius.template.data.datasources.implementation.MovieDataSource
import com.santimattius.template.data.datasources.implementation.RoomDataSource
import com.santimattius.template.data.dtoToEntity
import com.santimattius.template.data.entityToDomain
import com.santimattius.template.domain.entities.Movie
import com.santimattius.template.domain.repositories.MovieRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TMDbRepository : MovieRepository, KoinComponent {

    private val remoteDataSource: MovieDataSource by inject()
    private val localDataSource: RoomDataSource by inject()

    override suspend fun getPopular(): List<Movie> {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.getPopularMovies().getOrDefault(emptyList())
            localDataSource.save(movies.dtoToEntity())
        }
        return localDataSource.getAll().entityToDomain()
    }

    override suspend fun fetchPopular() = remoteDataSource.getPopularMovies().fold(onSuccess = {
        localDataSource.save(it.dtoToEntity())
        Result.success(localDataSource.getAll().entityToDomain())
    }, onFailure = {
        Result.failure(RefreshMovieFailed())
    })
}
