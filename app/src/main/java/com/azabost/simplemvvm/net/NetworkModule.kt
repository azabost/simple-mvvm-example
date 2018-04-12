package com.azabost.simplemvvm.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providesGitHubClient(gson: Gson): ApiClient {
        val retrofit = Retrofit.Builder()
                .baseUrl(GitHubService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val gitHubService = retrofit.create(GitHubService::class.java)
        return ApiClient(gitHubService)
    }

    @Singleton
    @Provides
    fun getGsonInstance(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }
}