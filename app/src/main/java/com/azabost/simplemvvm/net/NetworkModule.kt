package com.azabost.simplemvvm.net

import com.azabost.simplemvvm.net.connectivity.Connectivity
import com.azabost.simplemvvm.net.connectivity.ConnectivityModule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
    includes = [
        ConnectivityModule::class
    ]
)
class NetworkModule {
    @Provides
    @Singleton
    fun providesGitHubClient(gson: Gson, connectivity: Connectivity): ApiClient {
        val retrofit = Retrofit.Builder()
            .baseUrl(GitHubService.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val gitHubService = retrofit.create(GitHubService::class.java)
        return ApiClient(gitHubService, connectivity)
    }

    @Singleton
    @Provides
    fun getGsonInstance(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }
}