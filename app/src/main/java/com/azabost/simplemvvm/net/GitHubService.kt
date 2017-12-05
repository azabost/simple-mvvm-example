package com.azabost.simplemvvm.net

import com.azabost.simplemvvm.net.response.RepoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("repos/{owner}/{repo}")
    fun getRepo(
            @Path("owner") owner: String,
            @Path("repo") repo: String
    ): Observable<RepoResponse>

    companion object {
        val BASE_URL = "https://api.github.com/"
    }
}