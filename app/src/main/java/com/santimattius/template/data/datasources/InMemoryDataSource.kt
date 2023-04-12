package com.santimattius.template.data.datasources

import com.santimattius.template.data.datasources.implementation.MovieNoExists
import com.santimattius.template.data.entities.MovieEntity
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryDataSource : LocalDataSource {

    private val mutex = Mutex()

    private val movies = mutableListOf<MovieEntity>()

    override suspend fun getAll(): List<MovieEntity> {
        return mutex.withLock {
            this.movies
        }
    }

    override suspend fun isEmpty(): Boolean {
        return mutex.withLock {
            this.movies.isEmpty()
        }
    }

    override suspend fun save(movies: List<MovieEntity>): Result<Boolean> {
        return mutex.withLock {
            this.movies.addAll(movies)
            Result.success(true)
        }
    }

    override suspend fun find(id: Int): Result<MovieEntity> {
        return mutex.withLock {
            val result = this.movies.find { it.id == id }
            if (result == null) Result.failure(MovieNoExists()) else Result.success(result)
        }
    }

    override suspend fun delete(movie: MovieEntity): Result<Boolean> {
        return mutex.withLock {
            val result = this.movies.indexOf(movie)
            if (result == -1) {
                Result.failure(MovieNoExists())
            } else {
                this.movies.removeAt(result)
                Result.success(true)
            }
        }
    }

    override suspend fun update(movie: MovieEntity): Result<Boolean> {
        return mutex.withLock {
            val removed = this.movies.remove(movie)
            if (removed) {
                this.movies.add(movie)
            }
            Result.success(removed)
        }
    }
}