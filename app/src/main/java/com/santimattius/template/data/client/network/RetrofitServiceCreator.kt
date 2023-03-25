package com.santimattius.template.data.client.network

import com.santimattius.template.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RetrofitServiceCreator @Inject constructor() {

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(RequestInterceptor(BuildConfig.API_KEY))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    internal inline fun <reified S> create(): S {
        return retrofit.create()
    }

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org"
    }
}
