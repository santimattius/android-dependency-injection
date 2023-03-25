package com.santimattius.template.data.repositories

import com.santimattius.template.data.datasources.implementation.MovieDataSource
import com.santimattius.template.data.datasources.implementation.RoomDataSource
import com.santimattius.template.data.dtoToEntity
import com.santimattius.template.data.entityToDomain
import com.santimattius.template.domain.entities.Movie
import com.santimattius.template.domain.repositories.MovieRepository
import javax.inject.Inject

class TMDbRepository @Inject constructor() : MovieRepository {

    @Inject
    lateinit var remoteDataSource: MovieDataSource

    @Inject
    lateinit var localDataSource: RoomDataSource

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
