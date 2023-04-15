package com.santimattius.template.ui.home

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.santimattius.template.data.datasources.RemoteDataSource
import com.santimattius.template.data.entities.MovieDto
import com.santimattius.template.data.entities.Response
import com.santimattius.template.utils.JsonLoader

class FakeRemoteDataSource : RemoteDataSource {

    private val jsonLoader = JsonLoader()
    private val gson = GsonBuilder().create()

    override suspend fun getPopularMovies(): Result<List<MovieDto>> {
        return runCatching {
            val json = jsonLoader.load("movie_popular_response_success.json")
            gson.fromJson<Response<MovieDto>>(
                json,
                object : TypeToken<Response<MovieDto>>() {}.type
            ).results
        }
    }
}